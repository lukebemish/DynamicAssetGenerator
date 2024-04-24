package dev.lukebemish.dynamicassetgenerator.impl.fabriquilt.fabric;

import dev.lukebemish.dynamicassetgenerator.impl.DynamicAssetGenerator;
import dev.lukebemish.dynamicassetgenerator.impl.fabriquilt.FabriQuiltShared;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.packs.PackType;

public class DynamicAssetGeneratorFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DynamicAssetGenerator.init();
        FabriQuiltShared.registerForType(PackType.SERVER_DATA);
    }
}
