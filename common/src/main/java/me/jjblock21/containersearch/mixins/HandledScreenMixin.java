package me.jjblock21.containersearch.mixins;

import me.jjblock21.containersearch.CSConfig;
import me.jjblock21.containersearch.core.SearchManager;
import me.jjblock21.containersearch.core.SearchBarWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
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
    @Unique
    private SearchManager container_search$searchManager;

    /**
     * Override this in a superclass to enable search functionality
     * @return The inventory to search
     */
    @Unique
    protected Inventory container_search$getInventoryToSearch() {
        return null;
    }

    @Inject(method="<init>", at = @At("TAIL"))
    private void init(ScreenHandler handler, PlayerInventory inventory, Text title, CallbackInfo ci) {
        Inventory inv = container_search$getInventoryToSearch();
        if (inv == null || inv.size() < CSConfig.minSlotCount) return;

        HandledScreen<?> screen = (HandledScreen<?>)(Object)this;
        container_search$searchManager = new SearchManager(screen, inventory);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (container_search$searchManager != null) {
            container_search$searchManager.init(this::addDrawableChild);
        }
    }

    // this is called every time you interact with a slot (doesn't have to be clicking)
    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V",
        at = @At(value = "TAIL"))
    private void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        if (getFocused() instanceof SearchBarWidget) {
            setFocused(null);
        }
    }

    @Inject(method = "keyPressed", at = @At(value = "HEAD"), cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (getFocused() instanceof SearchBarWidget) {
            // skip checks for E to close and number keys to move items into your hotbar
            cir.setReturnValue(super.keyPressed(keyCode, scanCode, modifiers));
        }
    }

    // dummy constructor
    protected HandledScreenMixin(Text title) {
        super(title);
    }
}
