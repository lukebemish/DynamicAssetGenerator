package dev.lukebemish.dynamicassetgenerator.api.client.generators.texsources;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSource;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSourceDataHolder;
import dev.lukebemish.dynamicassetgenerator.api.client.image.ImageUtils;
import dev.lukebemish.dynamicassetgenerator.api.colors.operations.ColorOperations;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * A {@link TexSource} that modifies the alpha channel of a provided texture source based on the alpha channel of the
 * provided mask. The sources in {@link dev.lukebemish.dynamicassetgenerator.api.client.generators.texsources.mask} may
 * be useful for creating masks.
 */
public final class MaskSource implements TexSource {
    public static final MapCodec<MaskSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            TexSource.CODEC.fieldOf("input").forGetter(MaskSource::getInput),
            TexSource.CODEC.fieldOf("mask").forGetter(MaskSource::getMask)
    ).apply(instance, MaskSource::new));
    private final TexSource input;
    private final TexSource mask;

    private MaskSource(TexSource input, TexSource mask) {
        this.input = input;
        this.mask = mask;
    }

    @Override
    public @NonNull MapCodec<? extends TexSource> codec() {
        return CODEC;
    }

    @Override
    public @Nullable IoSupplier<NativeImage> getSupplier(TexSourceDataHolder data, ResourceGenerationContext context) {
        IoSupplier<NativeImage> input = this.getInput().getCachedSupplier(data, context);
        IoSupplier<NativeImage> mask = this.getMask().getCachedSupplier(data, context);

        if (input == null) {
            data.getLogger().error("Texture given was nonexistent...\n{}", this.getMask().stringify());
            return null;
        }
        if (mask == null) {
            data.getLogger().error("Texture given was nonexistent...\n{}", this.getInput().stringify());
            return null;
        }

        return () -> {
            try (NativeImage inImg = input.get();
                 NativeImage maskImg = mask.get()) {

                return ImageUtils.generateScaledImage(ColorOperations.MASK, List.of(inImg, maskImg));
            }
        };
    }

    public TexSource getInput() {
        return input;
    }

    public TexSource getMask() {
        return mask;
    }

    public static class Builder {
        private TexSource input;
        private TexSource mask;

        /**
         * Sets the input texture.
         */
        public Builder setInput(TexSource input) {
            this.input = input;
            return this;
        }

        /**
         * Sets the mask texture; the alpha of the input texture will be multiplied by the alpha of this texture.
         */
        public Builder setMask(TexSource mask) {
            this.mask = mask;
            return this;
        }

        public MaskSource build() {
            Objects.requireNonNull(input);
            Objects.requireNonNull(mask);
            return new MaskSource(input, mask);
        }
    }
}
