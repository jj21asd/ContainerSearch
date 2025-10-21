package me.jjblock21.containersearch.mixins;

import me.jjblock21.containersearch.core.SearchProvider;
import me.jjblock21.containersearch.core.SearchBarWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin extends Screen {
    /**
     * Override this in a super class to enable search functionality for that container
     */
    @Unique
    protected SearchProvider container_search$createSearchProvider() {
        return null;
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        SearchProvider provider = container_search$createSearchProvider();
        if (provider == null) return;

        // register custom widgets
        for (ClickableWidget widget : provider.createWidgets()) {
            addDrawableChild(widget);
            if (widget instanceof SearchBarWidget) {
                setFocused(widget);
            }
        }
    }

    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V",
        at = @At(value = "TAIL"))
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        setFocused(null);
    }

    @Inject(method = "keyPressed", at = @At(value = "HEAD"), cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (getFocused() instanceof SearchBarWidget) {
            // continue to handle navigation keys as well as forwarding key events to gui elements,
            // but intercept hotkeys like E to close
            super.keyPressed(keyCode, scanCode, modifiers);
            cir.setReturnValue(true);
        }
    }

    // dummy constructor
    protected HandledScreenMixin(Text title) {
        super(title);
    }
}
