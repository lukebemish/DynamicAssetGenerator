package dev.lukebemish.dynamicassetgenerator.impl.client.platform;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.resources.ResourceLocation;

public interface PlatformClient {
    void addSpriteSource(ResourceLocation location, MapCodec<? extends SpriteSource> codec);
}
