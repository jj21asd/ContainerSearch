package me.containersearch.mixin;

import me.containersearch.search.ContainerSearchHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {

    @Unique
    protected ContainerSearchHandler searchHandler;

    /*
     * Override the set of slots rendered only the ones matching the active search.
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;size()I"))
    private int render(NonNullList<Slot> instance) {
        if (searchHandler != null && searchHandler.hasCompletedSearch()) {
            return searchHandler.getLastResult().length;
        }
        return instance.size();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;get(I)Ljava/lang/Object;"))
    private Object getSlotToRender(NonNullList<Slot> instance, int i) {
        if (searchHandler != null && searchHandler.hasCompletedSearch()) {
            return searchHandler.getLastResult()[i];
        }
        return instance.get(i);
    }

    // Block key events if searchbar is focussed.
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void keyPressed(int key, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        // Forward the event to the search handler and exit with true if it wishes to consume the key press.
        if (searchHandler != null && searchHandler.keyPressed(key, j, k)) {
            cir.setReturnValue(true);
        }
    }

    // Notify the search handler when a slot is clicked.
    @Inject(method = "slotClicked", at = @At("TAIL"))
    private void slotClicked(Slot slot, int i, int j, ClickType clickType, CallbackInfo ci) {
        if (searchHandler != null) {
            searchHandler.slotClicked();
        }
    }

    /*
     * Dummy constructor and methods.
     */
    protected AbstractContainerScreenMixin(Component component) {
        super(component);
    }

    @Override @Unique
    public T getMenu() { return null; }
}
