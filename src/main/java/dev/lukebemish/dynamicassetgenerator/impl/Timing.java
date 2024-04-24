package dev.lukebemish.dynamicassetgenerator.impl;

import dev.lukebemish.dynamicassetgenerator.impl.platform.Services;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class Timing {
    private static final boolean[] LOGGED = new boolean[2];

    private static final Map<String, Map<ResourceLocation, Long>> partialTimes = new HashMap<>();

    public synchronized static void recordPartialTime(String cache, ResourceLocation location, long time) {
        if (!partialTimes.containsKey(cache)) {
            partialTimes.put(cache, new HashMap<>());
        }
        partialTimes.get(cache).put(location, time);
    }

    public synchronized static void recordTime(String cache, ResourceLocation location, long time) {
        var map = partialTimes.get(cache);
        if (map != null) {
            time = time + map.get(location);
        }
        if (!Files.exists(Services.PLATFORM.getModDataFolder())) {
            try {
                Files.createDirectories(Services.PLATFORM.getModDataFolder());
            } catch (IOException e) {
                if (!LOGGED[0]) {
                    DynamicAssetGenerator.LOGGER.error("Issue creating mod data folder", e);
                    LOGGED[0] = true;
                }
                return;
            }
        }
        Path file = Services.PLATFORM.getModDataFolder().resolve("times.log");
        if (!Files.exists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException e) {
                if (!LOGGED[1]) {
                    DynamicAssetGenerator.LOGGER.error("Issue writing to times.log", e);
                    LOGGED[1] = true;
                }
                return;
            }
        }
        try (var writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(cache + " " + location + " " + time + "\n");
        } catch (IOException e) {
            if (!LOGGED[1]) {
                DynamicAssetGenerator.LOGGER.error("Issue writing to times.log", e);
                LOGGED[1] = true;
            }
        }
    }
}
