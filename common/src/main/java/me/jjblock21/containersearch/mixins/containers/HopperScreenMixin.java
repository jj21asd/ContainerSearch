package me.jjblock21.containersearch.mixins.containers;

import net.minecraft.client.gui.screen.ingame.HopperScreen;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(HopperScreen.class)
public class HopperScreenMixin {
    @SoftOverride
    protected Inventory container_search$getInventoryToSearch() {
        HopperScreen screen = (HopperScreen)(Object)this;
        return ((HopperScreenHandlerAccessor)screen.getScreenHandler()).getInventory();
    }
}
