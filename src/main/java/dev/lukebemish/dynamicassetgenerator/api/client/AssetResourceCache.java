package dev.lukebemish.dynamicassetgenerator.api.client;

import dev.lukebemish.dynamicassetgenerator.api.ResourceCache;
import dev.lukebemish.dynamicassetgenerator.impl.DynamicAssetGenerator;
import dev.lukebemish.dynamicassetgenerator.impl.client.ForegroundExtractor;
import dev.lukebemish.dynamicassetgenerator.impl.client.TexSourceCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.jspecify.annotations.NonNull;

/**
 * A {@link ResourceCache} meant to provide resources for resource packs. Texture sources should only be used within
 * a class extending this, to ensure that caching is respected.
 */
public class AssetResourceCache extends ResourceCache {
    /**
     * The location of an empty texture, which consists of a single transparent pixel.
     */
    @SuppressWarnings("unused")
    public static final ResourceLocation EMPTY_TEXTURE = new ResourceLocation(DynamicAssetGenerator.MOD_ID, "empty");

    /**
     * @param name a unique identifier for this cache
     */
    public AssetResourceCache(ResourceLocation name) {
        super(name);
        this.planResetListener(TexSourceCache::reset);
        this.planResetListener(ForegroundExtractor::reset);
    }

    @Override
    public @NonNull PackType getPackType() {
        return PackType.CLIENT_RESOURCES;
    }
}
