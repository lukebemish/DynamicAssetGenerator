/*
 * Copyright (C) 2022-2023 Luke Bemish and contributors
 * SPDX-License-Identifier: LGPL-3.0-or-later
 */

package dev.lukebemish.dynamicassetgenerator.api.client.generators.texsources;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSource;
import dev.lukebemish.dynamicassetgenerator.api.client.generators.TexSourceDataHolder;
import dev.lukebemish.dynamicassetgenerator.api.client.image.ImageUtils;
import dev.lukebemish.dynamicassetgenerator.impl.client.NativeImageHelper;
import dev.lukebemish.dynamicassetgenerator.impl.util.Maath;
import dev.lukebemish.dynamicassetgenerator.impl.util.MultiCloser;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;

/**
 * A {@link TexSource} that splits any number of possibly animated textures into individual frames, and assembles an
 * output texture by combining the patterns of frames of the various inputs, applying the same operation to each
 * combination of frame textures as necessary. Individual frames can be captured within the generator using
 * {@link AnimationFrameCapture}.
 */
public final class AnimationSplittingSource implements TexSource {
    public static final Codec<AnimationSplittingSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, TimeAwareSource.CODEC).fieldOf("sources").forGetter(AnimationSplittingSource::getSources),
            TexSource.CODEC.fieldOf("generator").forGetter(AnimationSplittingSource::getGenerator)
    ).apply(instance, AnimationSplittingSource::new));
    private final Map<String, TimeAwareSource> sources;
    private final TexSource generator;

    private AnimationSplittingSource(Map<String, TimeAwareSource> sources, TexSource generator) {
        this.sources = sources;
        this.generator = generator;
    }

    @Override
    public Codec<? extends TexSource> codec() {
        return CODEC;
    }

    @Override
    public @Nullable IoSupplier<NativeImage> getSupplier(TexSourceDataHolder data, ResourceGenerationContext context) {
        Map<String, IoSupplier<NativeImage>> sources = new HashMap<>();
        Map<String, Integer> times = new HashMap<>();
        this.getSources().forEach((key, source) -> {
            sources.put(key, source.source().getCachedSupplier(data, context));
            times.put(key, source.scale());
        });
        if (sources.isEmpty()) {
            data.getLogger().error("No sources given...");
            return null;
        }
        return () -> {
            Map<String, NativeImage> images = new HashMap<>();
            for (Map.Entry<String, IoSupplier<NativeImage>> e : sources.entrySet()) {
                String key = e.getKey();
                images.put(key, e.getValue().get());
            }
            try (MultiCloser ignored = new MultiCloser(images.values())) {
                List<NativeImage> imageList = images.values().stream().toList();
                List<Integer> counts = images.entrySet().stream().map(entry -> times.get(entry.getKey()) * getFrameCount(entry.getValue())).toList();
                for (int i : counts) {
                    if (i == 0) {
                        data.getLogger().error("Source not shaped correctly for an animation...");
                        throw new IOException("Source not shaped correctly for an animation...");
                    }
                }
                int lcm = Maath.lcm(counts);
                int lcmWidth = Maath.lcm(imageList.stream().map(NativeImage::getWidth).toList());
                NativeImage output = NativeImageHelper.of(NativeImage.Format.RGBA, lcmWidth, lcmWidth * lcm, false);
                for (int i = 0; i < lcm; i++) {
                    Map<String, NativeImage> map = new HashMap<>();
                    int finalI = i;
                    images.forEach((str, old) -> map.put(str, getPartialImage(old, finalI, times.get(str))));
                    try (ImageCollection collection = new ImageCollection(map, this.getSources(), i)) {
                        TexSourceDataHolder newData = new TexSourceDataHolder(data);
                        newData.put(ImageCollection.class, collection);
                        IoSupplier<NativeImage> supplier = generator.getCachedSupplier(newData, context);
                        if (supplier == null) {
                            data.getLogger().error("Generator created no image...");
                            throw new IOException("Generator created no image...");
                        }
                        NativeImage supplied = supplier.get();
                        int sWidth = supplied.getWidth();
                        if (sWidth != supplied.getHeight()) {
                            data.getLogger().error("Generator created non-square image...\n{}", generator.stringify());
                            throw new IOException("Generator created non-square image...");
                        }
                        int scale = lcmWidth / sWidth;
                        for (int x = 0; x < lcmWidth; x++) {
                            for (int y = 0; y < lcmWidth; y++) {
                                int color = ImageUtils.safeGetPixelABGR(supplied, x / scale, y / scale);
                                output.setPixelRGBA(x, y + i * lcmWidth, color);
                            }
                        }
                    }
                }
                return output;
            }
        };
    }

    private static int getFrameCount(NativeImage image) {
        return image.getHeight() / image.getWidth();
    }


    private static NativeImage getPartialImage(NativeImage input, int part, int scale) {
        int numFull = input.getHeight() / input.getWidth();
        int size = input.getWidth();
        NativeImage output = NativeImageHelper.of(input.format(), size, size, false);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                output.setPixelRGBA(x, y, ImageUtils.safeGetPixelABGR(input, x, ((part / scale) % numFull) * size + y));
            }
        }
        return output;
    }

    public Map<String, TimeAwareSource> getSources() {
        return sources;
    }

    public TexSource getGenerator() {
        return generator;
    }


    @ApiStatus.Internal
    static class ImageCollection implements Closeable {
        private final Map<String, NativeImage> map;
        private final Map<String, TimeAwareSource> original;
        private final int frame;

        @ApiStatus.Internal
        private ImageCollection(Map<String, NativeImage> map, Map<String, TimeAwareSource> original, int frame) {
            this.map = new HashMap<>(map);
            this.original = original;
            this.frame = frame;
        }

        @Override
        public void close() {
            map.values().forEach(NativeImage::close);
        }

        public NativeImage get(String key) throws IOException {
            NativeImage input = map.get(key);
            if (input == null) throw new IOException("No image for key: " + key);
            NativeImage newImage = new NativeImage(input.format(), input.getWidth(), input.getHeight(), false);
            newImage.copyFrom(input);
            return newImage;
        }

        public int getFrame() {
            return frame;
        }

        public TimeAwareSource getFull(String key) {
            return original.get(key);
        }
    }

    /**
     * A source for an image to be split into frames, aware of how scaled this animation is in time.
     * @param source the source to split into frames
     * @param scale the scale of this animation in time
     */
    public record TimeAwareSource(TexSource source, int scale) {
        public static final Codec<TimeAwareSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                TexSource.CODEC.fieldOf("source").forGetter(TimeAwareSource::source),
                Codec.INT.optionalFieldOf("scale", 1).forGetter(TimeAwareSource::scale)
        ).apply(instance, TimeAwareSource::new));
    }

    public static class Builder {
        private Map<String, TimeAwareSource> sources;
        private TexSource generator;

        /**
         * Sets a map to sources to split into frames, from keys used to get frames of specific sources from within the
         * generator.
         */
        public Builder setSources(Map<String, TimeAwareSource> sources) {
            this.sources = sources;
            return this;
        }

        /**
         * Sets the generator to use to generate each frame of the animation. {@link AnimationFrameCapture} should be
         * used to capture frames of a specific source.
         */
        public Builder setGenerator(TexSource generator) {
            this.generator = generator;
            return this;
        }

        public AnimationSplittingSource build() {
            Objects.requireNonNull(sources);
            Objects.requireNonNull(generator);
            return new AnimationSplittingSource(sources, generator);
        }
    }
}
