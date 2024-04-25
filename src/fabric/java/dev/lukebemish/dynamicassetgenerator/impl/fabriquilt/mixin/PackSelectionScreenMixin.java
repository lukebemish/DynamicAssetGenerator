package dev.lukebemish.dynamicassetgenerator.impl.fabriquilt.mixin;

import dev.lukebemish.dynamicassetgenerator.impl.DynamicAssetGenerator;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.stream.Stream;

@Mixin(PackSelectionScreen.class)
public class PackSelectionScreenMixin {
    @ModifyVariable(method = "updateList", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private Stream<PackSelectionModel.Entry> dynamic_asset_generator$hidePacks(Stream<PackSelectionModel.Entry> pModels) {
        return pModels.filter(e -> !e.getId().startsWith(DynamicAssetGenerator.MOD_ID+"/"));
    }
}
