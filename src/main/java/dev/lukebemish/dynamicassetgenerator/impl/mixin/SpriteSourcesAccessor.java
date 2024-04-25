package dev.lukebemish.dynamicassetgenerator.impl.mixin;

import com.google.common.collect.BiMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpriteSources.class)
public interface SpriteSourcesAccessor {
    @SuppressWarnings("UnusedReturnValue")
    @Invoker("register")
    static SpriteSourceType dynamic_asset_generator$invokeRegister(String pName, MapCodec<? extends SpriteSource> mapCodec) {
        throw new AssertionError("Mixin failed to apply");
    }

    @Accessor("TYPES")
    static BiMap<ResourceLocation, SpriteSourceType> dynamic_asset_generator$getTypes() {
        throw new AssertionError("Mixin failed to apply");
    }
}
