package me.jjblock21.containersearch.mixins.containers;

import me.jjblock21.containersearch.core.SearchProvider;
import me.jjblock21.containersearch.mixins.HandledScreenMixin;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Implements search functionality for chests, barrels, enderchests, chest minecarts & boats
 */
@Mixin(GenericContainerScreen.class)
public class GenericContainerScreenMixin extends HandledScreenMixin {
    @Override
    protected SearchProvider container_search$createSearchProvider() {
        GenericContainerScreen screen = (GenericContainerScreen)(Object)this;
        return new SearchProvider(screen, screen.getScreenHandler().getInventory());
    }

    // dummy constructor
    protected GenericContainerScreenMixin(Text title) {
        super(title);
    }
}
