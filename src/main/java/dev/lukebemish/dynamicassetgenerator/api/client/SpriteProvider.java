package dev.lukebemish.dynamicassetgenerator.api.client;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.TrackingResourceSource;
import dev.lukebemish.dynamicassetgenerator.api.cache.CacheMetaJsonOps;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSource;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSourceDataHolder;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TextureMetaGenerator;
import dev.lukebemish.dynamicassetgenerator.impl.DynamicAssetGenerator;
import dev.lukebemish.dynamicassetgenerator.impl.ResourceCachingData;
import dev.lukebemish.dynamicassetgenerator.impl.client.ForegroundExtractor;
import dev.lukebemish.dynamicassetgenerator.impl.client.TexSourceCache;
import dev.lukebemish.dynamicassetgenerator.impl.client.platform.ClientServices;
import dev.lukebemish.dynamicassetgenerator.impl.mixin.SpriteSourcesAccessor;
import dev.lukebemish.dynamicassetgenerator.impl.util.ResourceUtils;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A sprite source which makes use of {@link TexSource}s to provide sprites at resource pack load. May be more reliable
 * than a {@link AssetResourceCache} for generating sprites based off of textures added by other mods which use runtime
 * resource generation techniques.
 */
public interface SpriteProvider<T extends SpriteProvider<T>> {
    /**
     * @return a map of texture location, not including the {@code "textures/"} prefix or file extension, to texture source
     */
    Map<ResourceLocation, TexSource> getSources(ResourceGenerationContext context);

    /**
     * @return a unique identifier for this sprite source type
     */
    ResourceLocation getLocation();

    /**
     * Registers a sprite source type.
     * @param location the location which this sprite source type can be referenced from a texture atlas JSON file with
     * @param codec a codec to provide instances of the type
     */
    static <T extends SpriteProvider<T>> void register(ResourceLocation location, MapCodec<T> codec) {
        ClientServices.PLATFORM_CLIENT.addSpriteSource(location, codec.xmap(SpriteProvider::wrap, Wrapper::unwrap));
    }

    /**
     * Registers a sprite source type
     * @param location the location which this sprite source type can be referenced from a texture atlas JSON file with
     * @param constructor supplies instances of this sprite source type
     */
    static <T extends SpriteProvider<T>> void register(ResourceLocation location, Supplier<T> constructor) {
        ClientServices.PLATFORM_CLIENT.addSpriteSource(location, MapCodec.unit(() -> constructor.get().wrap()));
    }

    /**
     * Will be run before generation starts. Allows for clearing of anything that saves state (caches or the like).
     * Implementations should call the super method to clear texture source and palette transfer caches.
     * @param context context for the generation that will occur after this source is reset
     */
    default void reset(ResourceGenerationContext context) {
        TexSourceCache.reset(context);
        ForegroundExtractor.reset(context);
    }

    default void run(ResourceManager resourceManager, SpriteSource.Output output, ResourceLocation cacheName) {
        ResourceGenerationContext context = new ResourceGenerationContext() {
            private final ResourceSource source = ResourceGenerationContext.ResourceSource.filtered(pack -> true, PackType.CLIENT_RESOURCES, resourceManager::listPacks)
                .fallback(new ResourceSource() {
                    @Override
                    public @Nullable IoSupplier<InputStream> getResource(@NonNull ResourceLocation location) {
                        return resourceManager.getResource(location).<IoSupplier<InputStream>>map(r -> r::open).orElse(null);
                    }

                    @Override
                    public List<IoSupplier<InputStream>> getResourceStack(@NonNull ResourceLocation location) {
                        return resourceManager.getResourceStack(location).stream().<IoSupplier<InputStream>>map(r -> r::open).toList();
                    }

                    @Override
                    public Map<ResourceLocation, IoSupplier<InputStream>> listResources(@NonNull String path, @NonNull Predicate<ResourceLocation> filter) {
                        Map<ResourceLocation, IoSupplier<InputStream>> map = new HashMap<>();
                        resourceManager.listResources(path, filter).forEach((rl, r) -> map.put(rl, r::open));
                        return map;
                    }

                    @Override
                    public Map<ResourceLocation, List<IoSupplier<InputStream>>> listResourceStacks(@NonNull String path, @NonNull Predicate<ResourceLocation> filter) {
                        Map<ResourceLocation, List<IoSupplier<InputStream>>> map = new HashMap<>();
                        resourceManager.listResourceStacks(path, filter).forEach((rl, r) -> map.put(rl, r.stream().<IoSupplier<InputStream>>map(i -> i::open).toList()));
                        return map;
                    }

                    @Override
                    public @NonNull Set<String> getNamespaces() {
                        return resourceManager.getNamespaces();
                    }
                });

            @Override
            public @NonNull ResourceLocation getCacheName() {
                return cacheName;
            }

            @Override
            public ResourceSource getResourceSource() {
                return source;
            }
        };

        this.reset(context);

        Map<ResourceLocation, TexSource> sources = getSources(context);

        sources.forEach((rl, texSource) -> {
            var trackingSource = TrackingResourceSource.of(context.getResourceSource(), "textures", ".png");
            ResourceGenerationContext trackingContext = context.withResourceSource(trackingSource);
            var dataHolder = new TexSourceDataHolder();
            IoSupplier<NativeImage> imageSupplier = ResourceUtils.wrapSafeData(
                new ResourceLocation(rl.getNamespace(), "textures/"+rl.getPath()+".png"),
                (r, c) -> texSource.getCachedSupplier(dataHolder, c),
                trackingContext,
                im -> {
                    try (var image = im) {
                        return new ByteArrayInputStream(image.asByteArray());
                    }
                },
                is -> {
                    try (var input = is) {
                        return NativeImage.read(input);
                    }
                },
                (r, c) -> {
                    CacheMetaJsonOps ops = new CacheMetaJsonOps();
                    ops.putData(ResourceCachingData.class, new ResourceCachingData(r, c));
                    return TexSource.CODEC.encodeStart(ops, texSource).result().map(DynamicAssetGenerator.GSON_FLAT::toJson).orElse(null);
                }
            );
            output.add(rl, spriteResourceLoader -> {
                try {
                    if (imageSupplier == null) {
                        throw new IOException("No image supplier");
                    }
                    final NativeImage image = imageSupplier.get();
                    AnimationMetadataSection section = AnimationMetadataSection.EMPTY;
                    if (!trackingSource.getTouchedTextures().isEmpty()) {
                        TextureMetaGenerator.AnimationGenerator generator = new TextureMetaGenerator.AnimationGenerator.Builder().build();
                        List<Pair<ResourceLocation, JsonObject>> animations = new ArrayList<>();
                        for (ResourceLocation touchedTexture : trackingSource.getTouchedTextures()) {
                            var resource = context.getResourceSource().getResource(new ResourceLocation(touchedTexture.getNamespace(), "textures/"+touchedTexture.getPath()+".png.mcmeta"));
                            if (resource == null) {
                                animations.add(new Pair<>(touchedTexture, null));
                                continue;
                            }
                            try (var reader = new BufferedReader(new InputStreamReader(resource.get()))) {
                                JsonObject json = DynamicAssetGenerator.GSON.fromJson(reader, JsonObject.class);
                                JsonObject animation = GsonHelper.getAsJsonObject(json, AnimationMetadataSection.SECTION_NAME);
                                animations.add(new Pair<>(touchedTexture, animation));
                            } catch (Exception ignored) {
                                animations.add(new Pair<>(touchedTexture, null));
                            }
                        }
                        JsonObject built = generator.apply(animations);
                        if (built != null) {
                            try {
                                section = AnimationMetadataSection.SERIALIZER.fromJson(built);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                    FrameSize frameSize = new FrameSize(image.getWidth(), image.getHeight());
                    if (section != AnimationMetadataSection.EMPTY) {
                        frameSize = section.calculateFrameSize(image.getWidth(), image.getHeight());
                    }
                    return new SpriteContents(rl, frameSize, image, new ResourceMetadata.Builder().put(AnimationMetadataSection.SERIALIZER, section).build());
                } catch (IOException e) {
                    DynamicAssetGenerator.LOGGER.error("Failed to generate texture for sprite source type "+getLocation()+" at "+rl+": ", e);
                    return null;
                }
            });
        });
    }

    @SuppressWarnings("unchecked")
    default Wrapper<T> wrap() {
        return new Wrapper<>((T) this, null);
    }

    final class Wrapper<T extends SpriteProvider<T>> implements SpriteSource {
        private final T source;
        private final @Nullable ResourceLocation location;

        private Wrapper(T source, @Nullable ResourceLocation location) {
            this.source = source;
            this.location = location;
        }

        @Override
        public void run(ResourceManager resourceManager, Output output) {
            ResourceLocation cacheName = this.location == null ?
                source.getLocation() :
                source.getLocation().withSuffix("__"+this.location.getNamespace()+"__"+this.location.getPath());
            source.run(resourceManager, output, cacheName);
        }

        @Override
        public @NonNull SpriteSourceType type() {
            return SpriteSourcesAccessor.dynamicassetgenerator$getTypes().get(source.getLocation());
        }

        public T unwrap() {
            return source;
        }

        @ApiStatus.Internal
        public Wrapper<T> withLocation(ResourceLocation atlasLocation) {
            return new Wrapper<>(source, atlasLocation);
        }
    }
}
