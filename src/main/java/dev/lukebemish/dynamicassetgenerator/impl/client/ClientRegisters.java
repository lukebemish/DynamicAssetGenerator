package dev.lukebemish.dynamicassetgenerator.impl.client;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.MapCodec;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSource;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ClientRegisters {
    private ClientRegisters() {}
    public static final BiMap<ResourceLocation, MapCodec<? extends TexSource>> TEXSOURCES = HashBiMap.create();
}
