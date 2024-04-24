package dev.lukebemish.dynamicassetgenerator.impl.mixin;

import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PackRepository.class)
public interface PackRepositoryMixin {
    @Accessor(value = "selected")
    List<Pack> dynamic_asset_generator$getSelected();
}
