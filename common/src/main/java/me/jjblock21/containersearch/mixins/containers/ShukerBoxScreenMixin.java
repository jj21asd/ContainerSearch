package me.jjblock21.containersearch.mixins.containers;

import me.jjblock21.containersearch.core.SearchProvider;
import me.jjblock21.containersearch.mixins.HandledScreenMixin;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Implements search functionality for Shulkers
 */
@Mixin(ShulkerBoxScreen.class)
public class ShukerBoxScreenMixin extends HandledScreenMixin {
    @Override
    protected SearchProvider container_search$createSearchProvider() {
        ShulkerBoxScreen screen = (ShulkerBoxScreen)(Object)this;
        ShulkerBoxScreenHandlerAccessor accessor = (ShulkerBoxScreenHandlerAccessor)screen.getScreenHandler();
        return new SearchProvider(screen, accessor.getInventory());
    }

    // dummy constructor
    protected ShukerBoxScreenMixin(Text title) {
        super(title);
    }
}
