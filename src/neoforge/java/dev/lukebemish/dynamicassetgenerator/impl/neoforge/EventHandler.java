package dev.lukebemish.dynamicassetgenerator.impl.neoforge;

import dev.lukebemish.dynamicassetgenerator.impl.DynamicAssetGenerator;
import dev.lukebemish.dynamicassetgenerator.impl.GeneratedPackResources;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.List;
import java.util.Optional;

public class EventHandler {
    public static void addResourcePack(AddPackFindersEvent event) {
        DynamicAssetGenerator.LOGGER.info("Attempting pack insertion...");
        PackType type = event.getPackType();
        DynamicAssetGenerator.CACHES.forEach((location, info) -> {
            if (info.cache().getPackType() == type) {
                event.addRepositorySource(consumer -> {
                    var metadata = DynamicAssetGenerator.makeMetadata(info.cache());
                    var packMetadata = new Pack.Metadata(
                        metadata.description(),
                        PackCompatibility.COMPATIBLE,
                        FeatureFlagSet.of(),
                        List.of(),
                        true
                    );
                    var id = DynamicAssetGenerator.MOD_ID+'/'+ info.cache().getName();
                    Pack pack = new Pack(
                        new PackLocationInfo(id, Component.literal(id), PackSource.DEFAULT, Optional.empty()),
                        new GeneratedPackResources.GeneratedResourcesSupplier(info.cache()),
                        packMetadata,
                        new PackSelectionConfig(true, info.position(), true)
                    );
                    consumer.accept(pack);
                });
            }
        });
    }
}
