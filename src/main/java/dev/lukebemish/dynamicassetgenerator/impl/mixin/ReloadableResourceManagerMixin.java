package dev.lukebemish.dynamicassetgenerator.impl.mixin;

import dev.lukebemish.dynamicassetgenerator.impl.ResourceFinder;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableResourceManager.class)
public abstract class ReloadableResourceManagerMixin {
    @Shadow
    @Final
    private PackType type;

    @Inject(method = "createReload", at = @At(value = "HEAD"))
    private void dynamic_asset_generator$insertResourcePack(Executor preparationExecutor,
                                                            Executor reloadExecutor,
                                                            CompletableFuture<Unit> afterPreparation,
                                                            List<PackResources> packs,
                                                            CallbackInfoReturnable<ReloadInstance> cir) {
        ResourceFinder.INSTANCES[type.ordinal()] = packs::stream;
    }
}
