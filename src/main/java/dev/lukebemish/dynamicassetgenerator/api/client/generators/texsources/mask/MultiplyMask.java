package dev.lukebemish.dynamicassetgenerator.api.client.generators.texsources.mask;

import com.mojang.serialization.MapCodec;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSource;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.texsources.AbstractManyOperationSource;
import dev.lukebemish.dynamicassetgenerator.api.colors.operations.ColorOperations;
import dev.lukebemish.dynamicassetgenerator.api.colors.operations.PointwiseOperation;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * An {@link AbstractManyOperationSource} defined by {@link ColorOperations#MULTIPLY}.
 */
public class MultiplyMask extends AbstractManyOperationSource {
    public static final MapCodec<MultiplyMask> CODEC = AbstractManyOperationSource.makeCodec(MultiplyMask::new);

    private MultiplyMask(List<TexSource> sources) {
        super(sources);
    }

    @Override
    public @NonNull MapCodec<? extends TexSource> codec() {
        return CODEC;
    }

    @Override
    public PointwiseOperation.Any<Integer> getOperation() {
        return ColorOperations.MULTIPLY;
    }

    public static class Builder {
        private List<TexSource> sources;

        /**
         * Sets the sources whose values to multiply
         */
        public Builder setSources(List<TexSource> sources) {
            this.sources = sources;
            return this;
        }

        public MultiplyMask build() {
            Objects.requireNonNull(sources);
            return new MultiplyMask(sources);
        }
    }
}
