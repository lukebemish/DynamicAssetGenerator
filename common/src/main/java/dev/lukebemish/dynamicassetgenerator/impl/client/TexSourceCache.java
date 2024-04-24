package dev.lukebemish.dynamicassetgenerator.impl.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Either;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.cache.CacheMetaJsonOps;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSource;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSourceDataHolder;
import dev.lukebemish.dynamicassetgenerator.impl.CacheReference;
import dev.lukebemish.dynamicassetgenerator.impl.DynamicAssetGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TexSourceCache {
    private TexSourceCache() {
    }

    private static final Map<ResourceLocation, Map<String, CacheReference<Either<NativeImage, IOException>>>> MULTI_CACHE = new ConcurrentHashMap<>();

    @NonNull
    public static NativeImage fromCache(IoSupplier<NativeImage> supplier, TexSource source, ResourceGenerationContext context, TexSourceDataHolder data) throws IOException {
        var cache = MULTI_CACHE.computeIfAbsent(context.getCacheName(), k -> new ConcurrentHashMap<>());
        try {
            var dataOps = new CacheMetaJsonOps();
            dataOps.putData(TexSourceDataHolder.class, data);
            String cacheKey = TexSource.CODEC.encodeStart(dataOps, source).result().map(DynamicAssetGenerator.GSON_FLAT::toJson).orElse(null);
            if (cacheKey == null) {
                return supplier.get();
            }
            CacheReference<Either<NativeImage, IOException>> ref;
            if (cache.containsKey(cacheKey))
                ref = cache.get(cacheKey);
            else
                ref = new CacheReference<>();
            var result = ref.calcSync(cached -> {
                if (cached == null) {
                    try {
                        NativeImage image = supplier.get();
                        ref.setHeld(Either.left(image));
                        NativeImage output = NativeImageHelper.of(image.format(), image.getWidth(), image.getHeight(), false);
                        output.copyFrom(image);
                        return Either.left(output);
                    } catch (IOException e) {
                        ref.setHeld(Either.right(e));
                        return Either.right(e);
                    }
                } else if (cached.left().isPresent()) {
                    NativeImage image = cached.left().get();
                    NativeImage output = NativeImageHelper.of(image.format(), image.getWidth(), image.getHeight(), false);
                    output.copyFrom(image);
                    return Either.left(output);
                } else {
                    return Either.right(cached.right().get());
                }
            });

            if (!cache.containsKey(cacheKey))
                cache.put(cacheKey, ref);

            if (result.left().isPresent()) {
                return result.left().get();
            } else {
                throw result.right().get();
            }
        } catch (RuntimeException e) {
            DynamicAssetGenerator.LOGGER.warn("Could not cache texture source; something has gone wrong with encoding or texture creation.", e);
            return supplier.get();
        }
    }

    public static void reset(ResourceGenerationContext context) {
        synchronized (MULTI_CACHE) {
            Map<String, CacheReference<Either<NativeImage, IOException>>> cache;
            if ((cache = MULTI_CACHE.get(context.getCacheName())) != null) {
                cache.forEach((s, e) -> {
                    if (e.getHeld().left().isPresent()) {
                        e.getHeld().left().get().close();
                    }
                });
                MULTI_CACHE.remove(context.getCacheName());
            }
        }
    }

}
