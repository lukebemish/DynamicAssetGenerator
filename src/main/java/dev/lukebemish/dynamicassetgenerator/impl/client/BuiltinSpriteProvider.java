package dev.lukebemish.dynamicassetgenerator.impl.client;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.client.SpriteProvider;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSource;
import dev.lukebemish.dynamicassetgenerator.impl.DynamicAssetGenerator;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record BuiltinSpriteProvider(Map<ResourceLocation, TexSource> sources, @Nullable ResourceLocation location) implements SpriteProvider<BuiltinSpriteProvider> {
    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(DynamicAssetGenerator.MOD_ID, "tex_sources");
    public static final MapCodec<BuiltinSpriteProvider> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
        Codec.unboundedMap(ResourceLocation.CODEC, TexSource.CODEC).fieldOf("sources").forGetter(BuiltinSpriteProvider::sources),
        ResourceLocation.CODEC.optionalFieldOf("location").forGetter(s -> Optional.ofNullable(s.location()))
    ).apply(i, (sources, location) -> new BuiltinSpriteProvider(sources, location.orElse(null))));

    @Override
    public Map<ResourceLocation, TexSource> getSources(ResourceGenerationContext context) {
        Map<ResourceLocation, TexSource> outSources = new HashMap<>(sources());
        if (location != null) {
            FileToIdConverter converter = new FileToIdConverter(location.getNamespace() + "/" + location.getPath(), ".json");
            context.getResourceSource().listResources(location.getNamespace() + "/" + location.getPath(), rl -> rl.getPath().endsWith(".json")).forEach((fileRl, resource) -> {
                ResourceLocation rl = converter.fileToId(fileRl);
                try (var reader = new InputStreamReader(resource.get())) {
                    JsonElement json = DynamicAssetGenerator.GSON.fromJson(reader, JsonElement.class);
                    var result = TexSource.CODEC.parse(JsonOps.INSTANCE, json);
                    result.result().ifPresent(texSource -> outSources.put(rl, texSource));
                    result.error().ifPresent(partial ->
                        DynamicAssetGenerator.LOGGER.error("Failed to load tex source json for " + location + ": " + rl + ": " + partial.message()));
                } catch (IOException e) {
                    DynamicAssetGenerator.LOGGER.error("Failed to load tex source json for " + location + ": " + rl, e);
                }
            });
        }
        return outSources;
    }

    @Override
    public ResourceLocation getLocation() {
        return LOCATION;
    }
}
