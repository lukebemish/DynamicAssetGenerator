package dev.lukebemish.dynamicassetgenerator.api.colors.operations;

/**
 * An operation that can be applied to the entirety of several images by being applied at any given point, to generate
 * data for any given point
 * @param <T> the type of data that this operation generates
 */
public interface PointwiseOperation<T> {

    /**
     * Applies this operation to the given colors and in-bounds flags.
     * @param colors the colors of the images to apply this operation to at the given point
     * @param inBounds whether each image is in-bounds at the given point. At least one image will always be in-bounds
     * @return the data generated at this point
     */
    T apply(int[] colors, boolean[] inBounds);

    /**
     * @return how many images this operation expects to be applied to. Should return -1 if the operation can be applied
     * to any number of images
     */
    int expectedImages();

    /**
     * A pointwise operation that can be applied to a single image.
     */
    @FunctionalInterface
    interface Unary<T> extends PointwiseOperation<T> {
        T apply(int color, boolean isInBounds);

        @Override
        default int expectedImages() {
            return 1;
        }

        @Override
        default T apply(int[] colors, boolean[] inBounds) {
            if (colors.length != 1 || inBounds.length != 1)
                throw new IllegalArgumentException("Unary operation must have exactly one input image");
            return apply(colors[0], inBounds[0]);
        }

        /**
         * @return a new unary operation that always returns the value it is provided
         */
        static Unary<Integer> identity() {
            return (color, isInBounds) -> color;
        }

        /**
         * @return a new unary operation that applies first one operation, then another
         */
        static <T> Unary<T> chain(Unary<Integer> first, Unary<T> then) {
            return (color, isInBounds) -> then.apply(first.apply(color, isInBounds), isInBounds);
        }
    }

    /**
     * A pointwise operation that can be applied to two images.
     */
    @FunctionalInterface
    interface Binary<T> extends PointwiseOperation<T> {
        T apply(int firstColor, int secondColor, boolean isFirstInBounds, boolean isSecondInBounds);

        @Override
        default int expectedImages() {
            return 2;
        }

        @Override
        default T apply(int[] colors, boolean[] inBounds) {
            if (colors.length != 2 || inBounds.length != 2)
                throw new IllegalArgumentException("Binary operation must have exactly two input images");
            return apply(colors[0], colors[1], inBounds[0], inBounds[1]);
        }
    }

    /**
     * A pointwise operation that can be applied to three images.
     */
    @FunctionalInterface
    interface Ternary<T> extends PointwiseOperation<T> {
        T apply(int firstColor, int secondColor, int thirdColor, boolean isFirstInBounds, boolean isSecondInBounds, boolean isThirdInBounds);

        @Override
        default int expectedImages() {
            return 3;
        }

        @Override
        default T apply(int[] colors, boolean[] inBounds) {
            if (colors.length != 3 || inBounds.length != 3)
                throw new IllegalArgumentException("Ternary operation must have exactly three input images");
            return apply(colors[0], colors[1], colors[2], inBounds[0], inBounds[1], inBounds[2]);
        }
    }

    /**
     * A pointwise operation that can be applied to four images.
     */
    @FunctionalInterface
    interface Tertiary<T> extends PointwiseOperation<T> {
        T apply(int firstColor, int secondColor, int thirdColor, int fourthColor, boolean isFirstInBounds, boolean isSecondInBounds, boolean isThirdInBounds, boolean isFourthInBounds);

        @Override
        default int expectedImages() {
            return 4;
        }

        @Override
        default T apply(int[] colors, boolean[] inBounds) {
            if (colors.length != 4 || inBounds.length != 4)
                throw new IllegalArgumentException("Ternary operation must have exactly three input images");
            return apply(colors[0], colors[1], colors[2], colors[3], inBounds[0], inBounds[1], inBounds[2], inBounds[3]);
        }
    }

    /**
     * A pointwise operation that can be applied to any number of images.
     */
    @FunctionalInterface
    interface Any<T> extends PointwiseOperation<T> {
        @Override
        default int expectedImages() {
            return -1;
        }

        /**
         * @return a new {@link Unary} operation that applies this operation to the image it is provided
         */
        default Unary<T> unary() {
            return (color, isInBounds) -> apply(new int[] { color }, new boolean[] { isInBounds });
        }

        /**
         * @return a new {@link Binary} operation that applies this operation to the two images it is provided
         */
        default Binary<T> binary() {
            return (firstColor, secondColor, isFirstInBounds, isSecondInBounds) -> apply(new int[] { firstColor, secondColor }, new boolean[] { isFirstInBounds, isSecondInBounds });
        }

        /**
         * @return a new {@link Ternary} operation that applies this operation to the three images it is provided
         */
        default Ternary<T> ternary() {
            return (firstColor, secondColor, thirdColor, isFirstInBounds, isSecondInBounds, isThirdInBounds) -> apply(new int[] { firstColor, secondColor, thirdColor }, new boolean[] { isFirstInBounds, isSecondInBounds, isThirdInBounds });
        }

        /**
         * @return a new any operation that always returns all values it is provided
         */
        static Any<int[]> identity() {
            return (colors, inBounds) -> colors;
        }
    }
}
