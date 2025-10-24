package me.jjblock21.containersearch.fabric.mixins.containers;

import atonkish.reinfcore.client.gui.screen.ingame.ReinforcedStorageScreen;
import me.jjblock21.containersearch.core.SearchProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.SoftOverride;

/**
 * Implements search functionality for all containers from the Reinforced series of mods
 */
@Mixin(value = ReinforcedStorageScreen.class)
public class ReinforcedStorageScreenMixin {
    @SoftOverride
    protected SearchProvider container_search$createSearchProvider() {
        ReinforcedStorageScreen screen = (ReinforcedStorageScreen)(Object)this;
        return new SearchProvider(screen, screen.getScreenHandler().getInventory());
    }
}
