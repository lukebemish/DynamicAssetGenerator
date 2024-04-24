package dev.lukebemish.dynamicassetgenerator.api.generators;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jspecify.annotations.NonNull;

import java.io.InputStream;
import java.util.Set;

/**
 * A resource generator that generates no resources. Useful for overriding other resource generators in lower priority
 * packs.
 */
public class DummyGenerator implements ResourceGenerator {
    public static final DummyGenerator INSTANCE = new DummyGenerator();
    public static final MapCodec<DummyGenerator> CODEC = MapCodec.unit(INSTANCE);

    private DummyGenerator() {}

    @Override
    public IoSupplier<InputStream> get(ResourceLocation outRl, ResourceGenerationContext context) {
        return null;
    }

    @Override
    public @NonNull Set<ResourceLocation> getLocations(ResourceGenerationContext context) {
        return Set.of();
    }

    @Override
    public @NonNull <T> DataResult<T> persistentCacheData(DynamicOps<T> ops, ResourceLocation location, ResourceGenerationContext context) {
        return DataResult.success(ops.empty());
    }

    @Override
    public MapCodec<? extends ResourceGenerator> codec() {
        return CODEC;
    }
}
