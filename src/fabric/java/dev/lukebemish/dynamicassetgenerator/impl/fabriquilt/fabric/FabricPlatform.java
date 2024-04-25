package dev.lukebemish.dynamicassetgenerator.impl.fabriquilt.fabric;

import dev.lukebemish.dynamicassetgenerator.impl.fabriquilt.FabriQuiltShared;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

public class FabricPlatform implements FabriQuiltShared {
    public static final FabriQuiltShared INSTANCE = new FabricPlatform();

    @Override
    public void packForType(PackType type, RepositorySource source) {
        PackPlanner.forType(type).register(source);
    }

    @Override
    public boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    @Override
    public String modVersion(String id) {
        return FabricLoader.getInstance().getModContainer(id).orElseThrow().getMetadata().getVersion().getFriendlyString();
    }

    @Override
    public Path configDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public Path cacheDir() {
        return FabricLoader.getInstance().getGameDir().resolve(".cache");
    }

    @Override
    public Stream<PackResources> unpackPacks(Stream<? extends PackResources> packs) {
        return packs.map(Function.identity());
    }
}
