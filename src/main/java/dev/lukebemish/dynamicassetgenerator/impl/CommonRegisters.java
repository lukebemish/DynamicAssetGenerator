package dev.lukebemish.dynamicassetgenerator.impl;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.MapCodec;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class CommonRegisters {
    private CommonRegisters() {}
    public static final BiMap<ResourceLocation, MapCodec<? extends ResourceGenerator>> RESOURCEGENERATORS = HashBiMap.create();
}
