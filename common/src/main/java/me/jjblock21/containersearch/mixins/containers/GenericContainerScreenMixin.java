package me.jjblock21.containersearch.mixins.containers;

import me.jjblock21.containersearch.core.SearchProvider;
import me.jjblock21.containersearch.mixins.HandledScreenMixin;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Implements SearchAddon for Chests, Barrels, Enderchests, Chest Minecarts and Chest Boats.
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
