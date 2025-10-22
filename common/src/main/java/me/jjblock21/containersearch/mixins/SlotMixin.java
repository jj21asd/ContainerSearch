package me.jjblock21.containersearch.mixins;

import me.jjblock21.containersearch.core.SearchableItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(Slot.class)
public class SlotMixin implements SearchableItem {
    @Unique private Consumer<Inventory> container_search$modifyListener;
    @Unique private boolean container_search$matching = true;

    @Override
    public void container_search$setModifyListener(Consumer<Inventory> listener) {
        container_search$modifyListener = listener;
    }

    @Unique
    @Override
    public void container_search$setMatching(boolean value) {
        container_search$matching = value;
    }

    @Inject(method = "markDirty", at = @At(value = "HEAD"))
    private void markDirty(CallbackInfo ci) {
        if (container_search$modifyListener != null) {
            container_search$modifyListener.accept(((Slot)(Object)this).inventory);
        }
    }

    @Inject(method = "isEnabled", at = @At(value = "RETURN"), cancellable = true)
    private void isEnabled(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValueZ() && container_search$matching);
    }
}
