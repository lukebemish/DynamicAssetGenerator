package dev.lukebemish.dynamicassetgenerator.impl;

import net.minecraft.server.packs.PackResources;

import java.util.stream.Stream;

@FunctionalInterface
public interface ResourceFinder {
    ResourceFinder[] INSTANCES = new ResourceFinder[2];

    Stream<PackResources> getPacks();
}
