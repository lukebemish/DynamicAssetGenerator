/*
 * Copyright (C) 2022-2023 Luke Bemish and contributors
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */

package dev.lukebemish.dynamicassetgenerator.impl.quilt;

import dev.lukebemish.dynamicassetgenerator.impl.DynamicAssetGenerator;
import dev.lukebemish.dynamicassetgenerator.impl.platform.services.Platform;
import com.google.auto.service.AutoService;
import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

@AutoService(Platform.class)
public class PlatformImpl implements Platform {
    private static final String MOD_VERSION = QuiltLoader.getModContainer(DynamicAssetGenerator.MOD_ID).orElseThrow().metadata().version().raw();

    public Path getConfigFolder() {
        return QuiltLoader.getConfigDir();
    }

    @Override
    public Path getModDataFolder() {
        return QuiltLoader.getCacheDir().resolve(DynamicAssetGenerator.MOD_ID);
    }

    @Override
    public String getModVersion() {
        return MOD_VERSION;
    }

}