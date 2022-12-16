/*
 * Copyright (C) 2022 Luke Bemish and contributors
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */

package dev.lukebemish.dynamicassetgenerator.impl.client;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.Codec;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.ITexSource;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSourceDataHolder;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;

public record TexSourceCachingWrapper(ITexSource wrapped) implements ITexSource {
    @Override
    public Codec<TexSourceCachingWrapper> codec() {
        return wrapped.codec().xmap(TexSourceCachingWrapper::new, wrapper -> unsafeCast(wrapper.wrapped));
    }

    @SuppressWarnings("unchecked")
    private static <T extends ITexSource> T unsafeCast(ITexSource source) {
        return (T) source;
    }

    @Override
    public @Nullable IoSupplier<NativeImage> getSupplier(TexSourceDataHolder data, ResourceGenerationContext context) {
        IoSupplier<NativeImage> wrapperImage = wrapped.getSupplier(data, context);
        if (wrapperImage == null) return null;
        return () -> TexSourceCache.fromCache(wrapperImage, wrapped, context);
    }
}
