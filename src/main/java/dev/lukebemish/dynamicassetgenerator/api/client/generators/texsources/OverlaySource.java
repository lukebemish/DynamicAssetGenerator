package dev.lukebemish.dynamicassetgenerator.api.client.generators.texsources;

import com.mojang.serialization.MapCodec;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSource;
import dev.lukebemish.dynamicassetgenerator.api.colors.operations.ColorOperations;
import dev.lukebemish.dynamicassetgenerator.api.colors.operations.PointwiseOperation;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * A {@link AbstractManyOperationSource} defined by {@link ColorOperations#OVERLAY}.
 */
public class OverlaySource extends AbstractManyOperationSource {
    public static final MapCodec<OverlaySource> CODEC = AbstractManyOperationSource.makeCodec(OverlaySource::new);

    private OverlaySource(List<TexSource> sources) {
        super(sources);
    }

    @Override
    public @NonNull MapCodec<? extends TexSource> codec() {
        return CODEC;
    }

    @Override
    public PointwiseOperation.Any<Integer> getOperation() {
        return ColorOperations.OVERLAY;
    }

    public static class Builder {
        private List<TexSource> sources;

        /**
         * Sets the sources to overlay, from highest to lowest.
         */
        public Builder setSources(List<TexSource> sources) {
            this.sources = sources;
            return this;
        }

        public OverlaySource build() {
            Objects.requireNonNull(sources);
            return new OverlaySource(sources);
        }
    }
}
