package dev.lukebemish.dynamicassetgenerator.impl;

import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import net.minecraft.resources.ResourceLocation;

public record ResourceCachingData(ResourceLocation location, ResourceGenerationContext context) {}
