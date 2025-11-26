package me.jjblock21.containersearch.mixins.containers;

import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

/**
 * Implements search functionality for chests, barrels, enderchests, chest minecarts & boats
 */
@Mixin(GenericContainerScreen.class)
public class GenericContainerScreenMixin {
    @SoftOverride
    protected Inventory container_search$getInventoryToSearch() {
        GenericContainerScreen screen = (GenericContainerScreen)(Object)this;
        return screen.getScreenHandler().getInventory();
    }
}
