package me.jjblock21.containersearch.core;

import joptsimple.internal.Strings;
import me.jjblock21.containersearch.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

/**
 * Encapsulates shared search logic for each screen
 */
public class SearchProvider {
    private final HandledScreen<?> screen;
    private final Inventory targetInventory;
    private SearchBarWidget searchBar;

    public SearchProvider(HandledScreen<?> screen, Inventory inventory) {
        this.screen = screen;
        this.targetInventory = inventory;
        this.searchBar = null;

        for (Slot slot : screen.getScreenHandler().slots) {
            ((SearchableItem)slot).container_search$setModifyListener(this::onSearchableItemModified);
        }
    }

    public ClickableWidget[] createWidgets() {
        int panelHeight = 14;
        int panelWidth = ModConfig.searchBarWidth /*+ panelHeight + 4*/;

        int panelX = ModConfig.panelOffsetX + (screen.width - panelWidth) / 2;
        int panelY = ModConfig.panelOffsetY;
        if (ModConfig.panelAlignment == ModConfig.Alignment.MENU) {
            panelY += screen.y - panelHeight - 2;
        } else {
            panelY += 2;
        }

        searchBar = new SearchBarWidget(MinecraftClient.getInstance().textRenderer, panelX, panelY + 1,
            ModConfig.searchBarWidth, panelHeight - 2, Text.empty());
        searchBar.setChangedListener(this::onSearchTextChanged);

        // in case I ever want to add this button back
        /*TexturedButtonWidget settingsButton = new TexturedButtonWidget(panelX + 120 + 3, panelY - 1, panelHeight + 2,
            panelHeight + 2, 0, 0, 16, Constants.WIDGET_SPRITES, 32, 32, this::onSettingsButtonClick);*/

        return new ClickableWidget[] {
            searchBar
        };
    }

    private void onSearchTextChanged(String text) {
        performSearch(text);
    }

    private void onSearchableItemModified(Inventory inventory) {
        if (inventory == targetInventory && searchBar != null) {
            performSearch(searchBar.getText());
        }
    }

    private void performSearch(String text) {
        if (Strings.isNullOrEmpty(text)) {
            restoreInventory();
            return;
        }

        SearchEngine.Query query = SearchEngine.Query.fromString(text);
        for (Slot slot : screen.getScreenHandler().slots) {
            if (slot.inventory != targetInventory) continue;

            boolean matching = SearchEngine.matchesItem(query, slot.getStack());
            ((SearchableItem)slot).container_search$setMatching(matching);
        }
    }

    private void restoreInventory() {
        for (Slot slot : screen.getScreenHandler().slots) {
            ((SearchableItem)slot).container_search$setMatching(true);
        }
    }
}
