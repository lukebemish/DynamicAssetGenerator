package dev.lukebemish.dynamicassetgenerator.impl.platform.services;

import net.minecraft.server.packs.PackResources;

import java.util.stream.Stream;

public interface ResourceDegrouper {
    Stream<PackResources> unpackPacks(Stream<PackResources> packs);
}
