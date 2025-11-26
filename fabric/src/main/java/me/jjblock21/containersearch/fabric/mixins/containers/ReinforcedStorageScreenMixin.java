package me.jjblock21.containersearch.fabric.mixins.containers;

import atonkish.reinfcore.client.gui.screen.ingame.ReinforcedStorageScreen;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.SoftOverride;

/**
 * Implements search functionality for all containers from the Reinforced series of mods
 */
@Pseudo
@Mixin(value = ReinforcedStorageScreen.class)
public class ReinforcedStorageScreenMixin {
    @SoftOverride
    protected Inventory container_search$getInventoryToSearch() {
        ReinforcedStorageScreen screen = (ReinforcedStorageScreen)(Object)this;
        return screen.getScreenHandler().getInventory();
    }
}
