package me.jjblock21.containersearch.fabric.mixins.containers;

import atonkish.reinfcore.client.gui.screen.ingame.ReinforcedStorageScreen;
import me.jjblock21.containersearch.core.SearchProvider;
import me.jjblock21.containersearch.mixins.HandledScreenMixin;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

/**
 * Implements search functionality for all containers from the Reinforced series of mods
 */
@Pseudo
@Mixin(value = ReinforcedStorageScreen.class, priority = 900)
public class ReinforcedStorageScreenMixin extends HandledScreenMixin {
    @Override
    protected SearchProvider container_search$createSearchProvider() {
        ReinforcedStorageScreen screen = (ReinforcedStorageScreen)(Object)this;
        return new SearchProvider(screen, screen.getScreenHandler().getInventory());
    }

    // dummy constructor
    protected ReinforcedStorageScreenMixin(Text title) {
        super(title);
    }
}
