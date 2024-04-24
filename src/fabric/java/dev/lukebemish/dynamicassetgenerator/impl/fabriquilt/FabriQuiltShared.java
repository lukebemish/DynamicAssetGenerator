package dev.lukebemish.dynamicassetgenerator.impl.fabriquilt;

import dev.lukebemish.dynamicassetgenerator.impl.DynamicAssetGenerator;
import dev.lukebemish.dynamicassetgenerator.impl.GeneratedPackResources;
import dev.lukebemish.dynamicassetgenerator.impl.fabriquilt.fabric.FabricPlatform;
import dev.lukebemish.dynamicassetgenerator.impl.fabriquilt.quilt.QuiltPlatformMinimal;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.flag.FeatureFlagSet;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface FabriQuiltShared {
    static FabriQuiltShared getInstance() {
        if (FabricLoader.getInstance().isModLoaded("quilt_loader")) {
            return QuiltPlatformMinimal.INSTANCE;
        } else {
            return FabricPlatform.INSTANCE;
        }
    }

    void packForType(PackType type, RepositorySource source);

    boolean isModLoaded(String id);
    String modVersion(String id);
    Path configDir();
    Path cacheDir();

    static void registerForType(PackType type) {
        getInstance().packForType(type, consumer ->
            DynamicAssetGenerator.CACHES.forEach(((location, info) -> {
                if (info.cache().getPackType() == type) {
                    var metadata = DynamicAssetGenerator.makeMetadata(info.cache());
                    var packMetadata = new Pack.Metadata(
                        metadata.description(),
                        PackCompatibility.COMPATIBLE,
                        FeatureFlagSet.of(),
                        List.of()
                    );
                    var id = DynamicAssetGenerator.MOD_ID+'/'+ info.cache().getName();
                    Pack pack = new Pack(
                        new PackLocationInfo(id, Component.literal(id), PackSource.DEFAULT, Optional.empty()),
                        new GeneratedPackResources.GeneratedResourcesSupplier(info.cache()),
                        packMetadata,
                        new PackSelectionConfig(true, info.position(), true)
                    );
                    consumer.accept(pack);
                }
            })));
    }

    Stream<PackResources> unpackPacks(Stream<? extends PackResources> packs);
}
