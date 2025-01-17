package dev.lukebemish.dynamicassetgenerator.impl;

import dev.lukebemish.dynamicassetgenerator.api.DataResourceCache;
import net.minecraft.resources.ResourceLocation;

public class BuiltinDataResourceCache extends DataResourceCache {
    public BuiltinDataResourceCache(ResourceLocation name) {
        super(name);
        this.planSource(() -> new JsonResourceGeneratorReader(context -> JsonResourceGeneratorReader.getSourceJsons(DynamicAssetGenerator.SOURCE_JSON_DIR, context)));
    }
}
