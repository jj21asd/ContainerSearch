package me.jjblock21.containersearch.mixin;

import me.jjblock21.containersearch.search.SearchComponent;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {

    @Unique
    protected SearchComponent containersearch$searchHandler;

    /*
     * Override the set of slots rendered only the ones matching the active search.
     */
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;size()I"))
    private int render(DefaultedList<Slot> instance) {
        if (containersearch$searchHandler != null && containersearch$searchHandler.hasCompletedSearch()) {
            return containersearch$searchHandler.getLastResult().length;
        }
        return instance.size();
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/collection/DefaultedList;get(I)Ljava/lang/Object;"))
    private Object getSlotToRender(DefaultedList<Slot> instance, int i) {
        if (containersearch$searchHandler != null && containersearch$searchHandler.hasCompletedSearch()) {
            return containersearch$searchHandler.getLastResult()[i];
        }
        return instance.get(i);
    }

    // Block key events if searchbar is focussed.
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void keyPressed(int key, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        // Forward the event to the search handler and exit with true if it wishes to consume the key press.
        if (containersearch$searchHandler != null && containersearch$searchHandler.keyPressed(key, j, k)) {
            cir.setReturnValue(true);
        }
    }

    // Notify the search handler when a slot is clicked.
    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("TAIL"))
    private void slotClicked(Slot slot, int i, int j, SlotActionType clickType, CallbackInfo ci) {
        if (containersearch$searchHandler != null) {
            containersearch$searchHandler.slotClicked();
        }
    }

    /*
     * Dummy constructor and methods.
     */
    protected HandledScreenMixin(Text component) {
        super(component);
    }

    @Override @Unique
    public T getScreenHandler() { return null; }
}
