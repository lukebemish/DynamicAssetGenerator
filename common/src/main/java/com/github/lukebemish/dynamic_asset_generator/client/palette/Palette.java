package com.github.lukebemish.dynamic_asset_generator.client.palette;

import com.github.lukebemish.dynamic_asset_generator.client.util.IPalettePlan;
import com.github.lukebemish.dynamic_asset_generator.DynamicAssetGenerator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Palette {
    private final ArrayList<ColorHolder> colors;
    private final float inPaletteCutoff;

    public Palette(float inPaletteCutoff) {
        this.colors = new ArrayList<>();
        this.inPaletteCutoff = inPaletteCutoff;
    }
    public Palette(List<ColorHolder> colors) {
        this.colors = new ArrayList<>(colors);
        this.colors.sort(ColorHolder::compareTo);
        this.inPaletteCutoff = 5f/255f;
    }

    public boolean isInPalette(ColorHolder color) {
        if (color.getA() == 0) return false;
        for (ColorHolder c : colors) {
            if ((Math.abs(c.getR()-color.getR()) < this.inPaletteCutoff) &&
                    (Math.abs(c.getG()-color.getG()) < this.inPaletteCutoff) &&
                    (Math.abs(c.getB()-color.getB()) < this.inPaletteCutoff)) {
                return true;
            }
        }
        return false;
    }

    public ColorHolder getColor(int i) {
        return colors.get(i);
    }

    public Stream<ColorHolder> getStream() {
        return colors.stream();
    }

    public void tryAdd(ColorHolder c) {
        if (!this.isInPalette(c)) {
            colors.add(c);
            Collections.sort(colors);
        }
    }

    public ColorHolder averageColor() {
        int t = 0;
        float r = 0;
        float g = 0;
        float b = 0;
        for (ColorHolder c : colors) {
            t++;
            r += c.getR();
            g += c.getG();
            b += c.getB();
        }
        return new ColorHolder(r/t,g/t,b/t);
    }

    public int closestTo(ColorHolder holder) {
        int index = 0;
        int outIndex = 0;
        double minDist = 2d;
        for (ColorHolder c : colors) {
            if (c.distanceToLab(holder) < minDist) {
                outIndex = index;
                minDist = c.distanceToLab(holder);
            }
            index++;
        }
        return outIndex;
    }

    public static Palette extractPalette(BufferedImage image, int extend) {
        int w = image.getWidth();
        int h = image.getHeight();
        Palette palette = new Palette(5f/255f);
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int c_int = image.getRGB(x,y);
                ColorHolder c = ColorHolder.fromColorInt(c_int);
                if (c.getA()!=0 && !palette.isInPalette(c)) {
                    palette.colors.add(new ColorHolder(c.getR(), c.getG(), c.getB()));
                }
            }
        }
        palette.extendPalette(extend);
        return palette;
    }

    public Palette extendPalette(int extend) {
        Collections.sort(this.colors);
        // Extend the palette if necessary (if it's less than 6 colors)
        while (this.colors.size() < extend) {
            ColorHolder high = this.colors.get(this.colors.size()-1);
            ColorHolder low = this.colors.get(0);
            ColorHolder highNew = new ColorHolder(high.getR() * .9f + .1f, high.getG() * .9f + .1f, high.getB() * .9f + .1f);
            ColorHolder lowNew = new ColorHolder(low.getR() * .9f, low.getG() * .9f, low.getB() * .9f);
            this.colors.add(highNew);
            this.colors.add(0,lowNew);
        }
        return this;
    }

    public ColorHolder getColorAtRamp(float pos) {
        int index = Math.round(pos*(colors.size()-1));
        return colors.get(index);
    }

    public int getSize() {
        return colors.size();
    }

    public static BufferedImage paletteCombinedImage(IPalettePlan plan) {
        boolean includeBackground = plan.includeBackground();
        int extend = plan.extend();
        try {
            BufferedImage b_img = plan.getBackground();
            BufferedImage o_img = plan.getOverlay();
            BufferedImage p_img = plan.getPaletted();
            //We don't care if they're square; we'll crop them. Currently, likely breaks animations...
            int o_dim = Math.min(o_img.getHeight(),o_img.getWidth());
            int b_dim = Math.min(b_img.getHeight(),b_img.getWidth());
            int p_dim = Math.min(p_img.getHeight(),p_img.getWidth());
            int w = Math.max(o_dim, Math.max(b_dim,p_dim));
            int os = w/o_img.getWidth();
            int bs = w/b_img.getWidth();
            int ps = w/p_img.getWidth();
            BufferedImage out_img = new BufferedImage(w,w,BufferedImage.TYPE_INT_ARGB);
            Palette backgroundPalette = extractPalette(b_img, extend);
            ColorHolder maxPaletteKey = new ColorHolder(0,0,0);
            ColorHolder minPaletteKey = new ColorHolder(1,1,1);
            if (plan.stretchPaletted()) {
                for (int x = 0; x < w; x++) {
                    for (int y = 0; y < w; y++) {
                        ColorHolder colorThis = ColorHolder.fromColorInt(p_img.getRGB(x / ps, y / ps)).withA(1.0f);
                        if (colorThis.compareTo(maxPaletteKey) > 0) {
                            maxPaletteKey = colorThis;
                        } else if (colorThis.compareTo(minPaletteKey) < 0) {
                            minPaletteKey = colorThis;
                        }
                    }
                }
            }
            float maxAvg = (maxPaletteKey.getR()+maxPaletteKey.getG()+maxPaletteKey.getB())/3f;
            float minAvg = (minPaletteKey.getR()+minPaletteKey.getG()+minPaletteKey.getB())/3f;
            float range = maxAvg - minAvg;
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < w; y++) {
                    ColorHolder outVal;
                    if (includeBackground) {
                        outVal = ColorHolder.fromColorInt(b_img.getRGB(x / bs, y / bs));
                    } else {
                        outVal = new ColorHolder(0f,0f,0f,0f);
                    }
                    // Add color by palette
                    ColorHolder p_val = ColorHolder.fromColorInt(p_img.getRGB(x/ps,y/ps));
                    if (p_val.getA() > 0f) {
                        float ramp = (p_val.getR() + p_val.getG() + p_val.getB()) / 3f;
                        if (maxAvg > 0 && minAvg < 1 && range > 0) {
                            ramp = (ramp-minAvg)/range;
                        }
                        ColorHolder palettedColor = backgroundPalette.getColorAtRamp(ramp);
                        palettedColor = palettedColor.withA(p_val.getA());
                        outVal = ColorHolder.alphaBlend(palettedColor, outVal);
                    }
                    ColorHolder overlayC = ColorHolder.fromColorInt(o_img.getRGB(x/os,y/os));
                    outVal = ColorHolder.alphaBlend(overlayC, outVal);
                    out_img.setRGB(x,y,ColorHolder.toColorInt(outVal));
                }
            }
            return out_img;
        } catch (IOException e) {
            DynamicAssetGenerator.LOGGER.error("Error loading resources for image", e);
            return null;
        }
    }
}