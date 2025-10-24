package me.jjblock21.containersearch.core;

import joptsimple.internal.Strings;
import me.jjblock21.containersearch.ConfigModel;
import me.jjblock21.containersearch.mixins.HandledScreenAccessor;
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
            ((SlotExtension)slot).container_search$setModifyListener(this::onSlotModified);
        }
    }

    public ClickableWidget[] createWidgets() {
        int panelHeight = 14;
        int panelWidth = ConfigModel.searchBarWidth /*+ panelHeight + 4*/;

        int panelX = ConfigModel.panelOffsetX + (screen.width - panelWidth) / 2;
        int panelY = ConfigModel.panelOffsetY;

        if (ConfigModel.panelAlignment == ConfigModel.Alignment.MENU) {
            HandledScreenAccessor accessor = (HandledScreenAccessor)screen;
            panelY += Math.max(accessor.getY() - panelHeight - 2, 4);
        } else {
            panelY += 4;
        }

        searchBar = new SearchBarWidget(panelX, panelY + 1, ConfigModel.searchBarWidth, panelHeight - 2, Text.empty());
        searchBar.setChangedListener(this::onSearchTextChanged);

        // keep this here in case I ever want to add it back
        /*TexturedButtonWidget settingsButton = new TexturedButtonWidget(panelX + 120 + 3, panelY - 1, panelHeight + 2,
            panelHeight + 2, 0, 0, 16, Constants.WIDGET_SPRITES, 32, 32, this::onSettingsButtonClick);*/

        return new ClickableWidget[] {
            searchBar
        };
    }

    private void onSearchTextChanged(String text) {
        performSearch(text);
    }

    private void onSlotModified(Inventory inventory) {
        if (inventory == targetInventory && searchBar != null) {
            performSearch(searchBar.getText());
        }
    }

    private void performSearch(String text) {
        if (Strings.isNullOrEmpty(text)) {
            restoreInventory();
            return;
        }

        SearchHelper.Query query = SearchHelper.parseQuery(text);
        for (Slot slot : screen.getScreenHandler().slots) {
            if (slot.inventory == targetInventory) {
                boolean matching = SearchHelper.matchesItem(query, slot.getStack());
                ((SlotExtension) slot).container_search$setMatching(matching);
            }
        }
    }

    private void restoreInventory() {
        for (Slot slot : screen.getScreenHandler().slots) {
            ((SlotExtension)slot).container_search$setMatching(true);
        }
    }
}
