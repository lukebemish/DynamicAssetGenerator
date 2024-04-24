package dev.lukebemish.dynamicassetgenerator.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lukebemish.dynamicassetgenerator.impl.platform.Services;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public record ModConfig(boolean fullCache, int paletteForceClusteringCutoff, boolean timeResources, boolean keyedCache) {
    public static final Codec<ModConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.BOOL.fieldOf("full_cache").forGetter(ModConfig::fullCache),
        Codec.INT.fieldOf("palette_extraction_force_clustering_cutoff").forGetter(ModConfig::paletteForceClusteringCutoff),
        Codec.BOOL.fieldOf("time_resources").forGetter(ModConfig::timeResources),
        Codec.BOOL.fieldOf("keyed_cache").forGetter(ModConfig::keyedCache)
    ).apply(instance, ModConfig::new));
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    public static final Path FULL_PATH = Services.PLATFORM.getConfigFolder().resolve(DynamicAssetGenerator.MOD_ID+".json");

    private static ModConfig load() {
        ModConfig config = getDefault();
        try {
            checkExistence();
            JsonObject json = GSON.fromJson(Files.newBufferedReader(FULL_PATH), JsonObject.class);
            var either = CODEC.parse(JsonOps.INSTANCE, json).get();
            var left = either.left();
            if (left.isPresent()) {
                config = left.get();
            } else {
                DynamicAssetGenerator.LOGGER.error("Config is in the wrong format! An attempt to load with this config would crash. Using default config instead...");
            }
        } catch (IOException e) {
            DynamicAssetGenerator.LOGGER.error("Issue loading config", e);
        }
        try {
            config.save();
        } catch (IOException e) {
            DynamicAssetGenerator.LOGGER.error("Issue saving config", e);
        }
        return config;
    }

    public static ModConfig get() {
        return load();
    }

    private void save() throws IOException {
        if (!Files.exists(FULL_PATH.getParent())) Files.createDirectories(FULL_PATH.getParent());
        if (Files.exists(FULL_PATH)) {
            Files.delete(FULL_PATH);
        }
        Files.createFile(FULL_PATH);
        try (Writer writer = Files.newBufferedWriter(FULL_PATH)) {
            JsonElement json = CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow(false, e -> {});
            GSON.toJson(json, writer);
            writer.flush();
        }
    }

    private static void checkExistence() throws IOException {
        if (!Files.exists(FULL_PATH.getParent())) Files.createDirectories(FULL_PATH.getParent());
        if (!Files.exists(FULL_PATH)) {
            getDefault().save();
        }
    }

    private static ModConfig getDefault() {
        return new ModConfig(false, 1_000_000, false, true);
    }
}
