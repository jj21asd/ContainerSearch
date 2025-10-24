package me.jjblock21.containersearch.forge.mixins.containers;

import com.progwml6.ironchest.client.screen.IronChestScreen;
import me.jjblock21.containersearch.core.SearchProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

/**
 * Implements search functionality for Iron Chests
 */
@Mixin(IronChestScreen.class)
public class IronChestScreenMixin {
    @SoftOverride
    protected SearchProvider container_search$createSearchProvider() {
        IronChestScreen screen = (IronChestScreen)(Object)this;
        return new SearchProvider(screen, screen.getScreenHandler().getContainer());
    }
}
