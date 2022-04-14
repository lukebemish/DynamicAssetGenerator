package com.github.lukebemish.dynamic_asset_generator.client.json;

import com.github.lukebemish.dynamic_asset_generator.client.api.json.DynamicTextureJson;
import com.github.lukebemish.dynamic_asset_generator.client.api.json.ITexSource;
import com.github.lukebemish.dynamic_asset_generator.client.palette.Palette;
import com.github.lukebemish.dynamic_asset_generator.client.util.IPalettePlan;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.mojang.blaze3d.platform.NativeImage;

import java.io.IOException;
import java.util.function.Supplier;

public class CombinedPaletteImage implements ITexSource {
    public static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Override
    public Supplier<NativeImage> getSupplier(String inputStr) throws JsonSyntaxException {
        PaletteInfo info = gson.fromJson(inputStr, PaletteInfo.class);
        PalettePlanner planner = PalettePlanner.of(info);
        if (planner == null) return null;
        return () -> Palette.paletteCombinedImage(planner);
    }

    public static class PaletteInfo {
        @Expose
        String source_type;
        @Expose
        public JsonObject overlay;
        @Expose
        public JsonObject background;
        @Expose
        public JsonObject paletted;
        @Expose
        public boolean include_background;
        @Expose
        public boolean stretch_paletted;
        @Expose
        public int extend_palette_size;
    }

    public static class PalettePlanner implements IPalettePlan {
        private final PaletteInfo info;
        private Supplier<NativeImage> overlay;
        private Supplier<NativeImage> background;
        private Supplier<NativeImage> paletted;

        private PalettePlanner(PaletteInfo info) {
            this.info = info;
        }

        public static PalettePlanner of(PaletteInfo info) {
            PalettePlanner out = new PalettePlanner(info);
            out.background = DynamicTextureJson.readSupplierFromSource(info.background);
            out.paletted = DynamicTextureJson.readSupplierFromSource(info.paletted);
            out.overlay = DynamicTextureJson.readSupplierFromSource(info.overlay);
            if (out.overlay == null || out.background == null || out.paletted == null) return null;
            return out;
        }

        @Override
        public NativeImage getBackground() throws IOException {
            return background.get();
        }

        @Override
        public NativeImage getOverlay() throws IOException {
            return overlay.get();
        }

        @Override
        public NativeImage getPaletted() throws IOException {
            return paletted.get();
        }

        @Override
        public boolean includeBackground() {
            return info.include_background;
        }

        @Override
        public boolean stretchPaletted() {
            return info.stretch_paletted;
        }

        @Override
        public int extend() {
            return info.extend_palette_size;
        }
    }
}
