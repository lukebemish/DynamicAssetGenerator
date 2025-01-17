package dev.lukebemish.dynamicassetgenerator.api.colors.operations;

import dev.lukebemish.dynamicassetgenerator.api.colors.ColorTypes;

/**
 * A collection of common pointwise operations on colors.
 */
public final class ColorOperations {
    private ColorOperations() {}

    /**
     * A pointwise operation that combines the alpha channel of the second color with the RGB channels of the first.
     */
    public static final PointwiseOperation.Binary<Integer> MASK = (i, m, iInBounds, mInBounds) -> {
        if (!mInBounds || !iInBounds)
            return 0;
        int maskAlpha = ColorTypes.ARGB32.alpha(m);
        int oldAlpha = ColorTypes.ARGB32.alpha(i);
        int newAlpha = maskAlpha * oldAlpha / 255;
        return (i & 0xFFFFFF) | ((newAlpha & 0xFF) << 24);
    };

    /**
     * A pointwise operation that overlays all provided colors, using alpha compositing. The first provided color is the
     * top layer, and the last provided color is the bottom layer.
     */
    public static final PointwiseOperation.Any<Integer> OVERLAY = (colors, inBounds) -> {
        if (colors.length == 0)
            return 0;
        int color = 0;
        for (int i = 0; i < colors.length; i++) {
            if (inBounds[i]) {
                color = ColorTypes.ARGB32.alphaBlend(color, colors[i]);
            }
        }
        return color;
    };

    /**
     * A pointwise operation that adds all provided colors together, clamping the result to 255.
     */
    public static final PointwiseOperation.Any<Integer> ADD = (colors, inBounds) -> {
        if (colors.length == 0)
            return 0;
        int alpha = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        for (int i = 0; i < colors.length; i++) {
            if (inBounds[i]) {
                alpha += ColorTypes.ARGB32.alpha(colors[i]);
                red += ColorTypes.ARGB32.red(colors[i]);
                green += ColorTypes.ARGB32.green(colors[i]);
                blue += ColorTypes.ARGB32.blue(colors[i]);
            }
        }
        return ColorTypes.ARGB32.color(ColorTypes.clamp8(alpha), ColorTypes.clamp8(red), ColorTypes.clamp8(green), ColorTypes.clamp8(blue));
    };

    /**
     * A pointwise operation that multiplies all provided colors together, scaling to a 0-255 range.
     */
    public static final PointwiseOperation.Any<Integer> MULTIPLY = (colors, inBounds) -> {
        if (colors.length == 0)
            return 0;
        float alpha = 255;
        float red = 255;
        float green = 255;
        float blue = 255;
        for (int i = 0; i < colors.length; i++) {
            if (inBounds[i]) {
                alpha *= ColorTypes.ARGB32.alpha(colors[i]) / 255f;
                red *= ColorTypes.ARGB32.red(colors[i]) / 255f;
                green *= ColorTypes.ARGB32.green(colors[i]) / 255f;
                blue *= ColorTypes.ARGB32.blue(colors[i]) / 255f;
            }
        }
        alpha = Math.round(alpha);
        red = Math.round(red);
        green = Math.round(green);
        blue = Math.round(blue);
        return ColorTypes.ARGB32.color(ColorTypes.clamp8((int) alpha), ColorTypes.clamp8((int) red), ColorTypes.clamp8((int) green), ColorTypes.clamp8((int) blue));
    };

    /**
     * A pointwise operation that inverts the color.
     */
    public static final PointwiseOperation.Unary<Integer> INVERT = (color, inBounds) -> {
        if (!inBounds)
            return 0;
        return ~color;
    };
}
