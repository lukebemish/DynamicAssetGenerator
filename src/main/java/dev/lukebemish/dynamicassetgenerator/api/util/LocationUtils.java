package dev.lukebemish.dynamicassetgenerator.api.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;

/**
 * A series of utilities for modifying {@link ResourceLocation}s
 */
@SuppressWarnings("unused")
public final class LocationUtils {
    private LocationUtils() {}

    /**
     * Separates a prefixed location into its first prefix and the rest of the location.
     * @param location The location to separate.
     * @return A pair of the first prefix and the rest of the location.
     */
    public static Pair<String, ResourceLocation> separatePrefix(ResourceLocation location) {
        String[] parts = location.getPath().split("/", 2);
        if (parts.length == 1)
            return Pair.of("", location);
        return new Pair<>(parts[0], ResourceLocation.fromNamespaceAndPath(location.getNamespace(), parts[1]));
    }

    /**
     * Separates the extension of a location from the rest of the path.
     * @param location The location to separate.
     * @return A pair of the extension and the location without the extension.
     */
    public static Pair<String, ResourceLocation> separateSuffix(ResourceLocation location) {
        int index = location.getPath().lastIndexOf('.');
        if (index == -1)
            return Pair.of("", location);
        return new Pair<>(location.getPath().substring(index + 1), ResourceLocation.fromNamespaceAndPath(location.getNamespace(), location.getPath().substring(0, index)));
    }
}
