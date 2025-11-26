package me.jjblock21.containersearch.fabric.mixins.containers;

import me.jjblock21.containersearch.core.SearchManager;
import net.minecraft.inventory.Inventory;
import nourl.mythicmetalsdecorations.client.MythicChestScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.*;

/**
 * Implements search functionality for all Mythic Metals Decorations containers
 */
@Pseudo
@Mixin(MythicChestScreen.class)
public class MythicChestScreenMixin {
    @SoftOverride
    protected Inventory container_search$getInventoryToSearch() {
        MythicChestScreen screen = (MythicChestScreen)(Object)this;
        return screen.getScreenHandler().chestInventory();
    }

    // make the menu leave space for the search bar
    // (does not work when even the minimum of 6 slots doesn't fit)
    @ModifyArg(
        method = "init",
        index = 2,
        at = @At(
            value = "INVOKE",
            target = "Lnourl/mythicmetalsdecorations/utils/ChestScreenSize;decompose(III)Lnourl/mythicmetalsdecorations/utils/ChestScreenSize;",
            remap = false
        )
    )
    private int getMaxHeight(int maxHeight) {
        return maxHeight - (SearchManager.TOTAL_PANEL_HEIGHT + 12); // magic value that works reasonably well
    }
}
