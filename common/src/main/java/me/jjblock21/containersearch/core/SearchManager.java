package me.jjblock21.containersearch.core;

import joptsimple.internal.Strings;
import me.jjblock21.containersearch.CSConfig;
import me.jjblock21.containersearch.mixins.HandledScreenAccessor;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

import java.util.function.Consumer;

/**
 * Encapsulates shared search logic for each screen
 */
public class SearchManager {
    private final HandledScreen<?> screen;
    private final Inventory playerInventory;
    private SearchBarWidget searchBar;

    public static final int PANEL_HEIGHT = 14;
    public static final int PANEL_MARGIN_TOP = 4;
    public static final int PANEL_MARGIN_BOTTOM = 2;
    public static final int TOTAL_PANEL_HEIGHT = PANEL_MARGIN_TOP + PANEL_HEIGHT + PANEL_MARGIN_BOTTOM;

    public SearchManager(HandledScreen<?> screen, Inventory playerInventory) {
        this.screen = screen;
        this.playerInventory = playerInventory;
        this.searchBar = null;

        for (Slot slot : screen.getScreenHandler().slots) {
            ((SearchableItem)slot).container_search$setModifyListener(this::onSlotModified);
        }
    }

    public void init(Consumer<ClickableWidget> drawableChildConsumer) {
        int panelWidth = CSConfig.searchBarWidth /*+ panelHeight + 4*/;
        int panelX = CSConfig.panelOffsetX + (screen.width - panelWidth) / 2;
        int panelY = CSConfig.panelOffsetY;

        if (CSConfig.panelAlignment == CSConfig.Alignment.MENU) {
            HandledScreenAccessor accessor = (HandledScreenAccessor)screen;
            panelY += Math.max(accessor.getY() - PANEL_HEIGHT - PANEL_MARGIN_BOTTOM, PANEL_MARGIN_TOP);
        } else {
            panelY += PANEL_MARGIN_TOP;
        }

        searchBar = new SearchBarWidget(panelX, panelY + 1, CSConfig.searchBarWidth, PANEL_HEIGHT - 2, Text.empty());
        searchBar.setChangedListener(this::onSearchTextChanged);
        drawableChildConsumer.accept(searchBar);
        screen.setFocused(searchBar);
    }

    private void onSearchTextChanged(String text) {
        performSearch(text);
    }

    private void onSlotModified(Inventory inventory) {
        if (inventory != this.playerInventory && searchBar != null) {
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
            if (slot.inventory != playerInventory) {
                boolean matching = SearchHelper.matchesItem(query, slot.getStack());
                ((SearchableItem)slot).container_search$setEnabled(matching);
            }
        }
    }

    private void restoreInventory() {
        for (Slot slot : screen.getScreenHandler().slots) {
            ((SearchableItem)slot).container_search$setEnabled(true);
        }
    }
}
