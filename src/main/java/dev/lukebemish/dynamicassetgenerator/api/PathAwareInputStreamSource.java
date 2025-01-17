package dev.lukebemish.dynamicassetgenerator.api;

import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NonNull;

import java.util.Set;

/**
 * An {@link InputStreamSource} that is aware of the locations it can provide input streams for.
 */
public interface PathAwareInputStreamSource extends InputStreamSource {

    /**
     * @return the locations that this {@link InputStreamSource} can provide resources at.
     */
    @NonNull
    Set<ResourceLocation> getLocations(ResourceGenerationContext context);
}
