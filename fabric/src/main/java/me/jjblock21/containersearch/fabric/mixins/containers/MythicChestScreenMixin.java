package me.jjblock21.containersearch.fabric.mixins.containers;

import me.jjblock21.containersearch.core.SearchProvider;
import me.jjblock21.containersearch.mixins.HandledScreenMixin;
import net.minecraft.text.Text;
import nourl.mythicmetalsdecorations.client.MythicChestScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

/**
 * Implements search functionality for all Mythic Metals Decorations containers
 */
@Pseudo
@Mixin(MythicChestScreen.class)
public class MythicChestScreenMixin extends HandledScreenMixin {
    @Override
    protected SearchProvider container_search$createSearchProvider() {
        MythicChestScreen screen = (MythicChestScreen)(Object)this;
        return new SearchProvider(screen, screen.getScreenHandler().chestInventory());
    }

    // dummy constructor
    protected MythicChestScreenMixin(Text title) {
        super(title);
    }
}
