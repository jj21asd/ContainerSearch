package me.jjblock21.containersearch.fabric.mixins.containers;

import me.jjblock21.containersearch.core.SearchProvider;
import nourl.mythicmetalsdecorations.client.MythicChestScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.SoftOverride;

/**
 * Implements search functionality for all Mythic Metals Decorations containers
 */
@Mixin(MythicChestScreen.class)
public class MythicChestScreenMixin {
    @SoftOverride
    protected SearchProvider container_search$createSearchProvider() {
        MythicChestScreen screen = (MythicChestScreen)(Object)this;
        return new SearchProvider(screen, screen.getScreenHandler().chestInventory());
    }
}
