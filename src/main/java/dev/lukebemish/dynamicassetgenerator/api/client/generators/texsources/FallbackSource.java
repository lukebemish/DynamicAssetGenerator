package dev.lukebemish.dynamicassetgenerator.api.client.generators.texsources;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSource;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSourceDataHolder;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.helpers.NOPLogger;

import java.io.IOException;
import java.util.Objects;

/**
 * A {@link TexSource} that attempts to provide one texture, but if it fails, will provide another.
 */
public final class FallbackSource implements TexSource {
    public static final MapCodec<FallbackSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            TexSource.CODEC.fieldOf("original").forGetter(FallbackSource::getOriginal),
            TexSource.CODEC.fieldOf("fallback").forGetter(FallbackSource::getFallback)
    ).apply(instance, FallbackSource::new));
    private final TexSource original;
    private final TexSource fallback;

    private FallbackSource(TexSource original, TexSource fallback) {
        this.original = original;
        this.fallback = fallback;
    }

    public @NonNull MapCodec<? extends TexSource> codec() {
        return CODEC;
    }

    @Override
    public @Nullable IoSupplier<NativeImage> getSupplier(TexSourceDataHolder data, ResourceGenerationContext context) {
        TexSourceDataHolder newData = new TexSourceDataHolder(data);
        newData.put(TexSourceDataHolder.LOGGER_TOKEN, NOPLogger.NOP_LOGGER);
        IoSupplier<NativeImage> original = this.getOriginal().getCachedSupplier(newData, context);
        IoSupplier<NativeImage> fallback = this.getFallback().getCachedSupplier(data, context);

        if (original == null && fallback == null) {
            data.getLogger().error("Both textures given were nonexistent...");
            return null;
        }

        return () -> {
            if (original != null) {
                try {
                    return original.get();
                } catch (IOException ignored) {
                }
            }
            if (fallback != null)
                return fallback.get();
            data.getLogger().error("Both textures given were unloadable...");
            throw new IOException("Both textures given were unloadable...");
        };
    }

    public TexSource getOriginal() {
        return original;
    }

    public TexSource getFallback() {
        return fallback;
    }

    public static class Builder {
        private TexSource original;
        private TexSource fallback;

        /**
         * Sets the original texture to use.
         */
        public Builder setOriginal(TexSource original) {
            this.original = original;
            return this;
        }

        /**
         * Sets the texture to use if the original cannot be constructed.
         */
        public Builder setFallback(TexSource fallback) {
            this.fallback = fallback;
            return this;
        }

        public FallbackSource build() {
            Objects.requireNonNull(original);
            Objects.requireNonNull(fallback);
            return new FallbackSource(original, fallback);
        }
    }
}
