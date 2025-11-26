package me.jjblock21.containersearch.mixins.containers;

import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

/**
 * Implements search functionality for Shulkers
 */
@Mixin(ShulkerBoxScreen.class)
public class ShulkerBoxScreenMixin {
    @SoftOverride
    protected Inventory container_search$getInventoryToSearch() {
        ShulkerBoxScreen screen = (ShulkerBoxScreen)(Object)this;
        return ((ShulkerBoxScreenHandlerAccessor)screen.getScreenHandler()).getInventory();
    }
}
