package dev.lukebemish.dynamicassetgenerator.api;

/**
 * A listener called when a resource cache is reset.
 */
@FunctionalInterface
public interface Resettable {
    /**
     * Resets some state associated with sources registered to the resource cache.
     */
    void reset(ResourceGenerationContext context);
}
