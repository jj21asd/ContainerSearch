package me.jjblock21.containersearch.search;

import me.jjblock21.containersearch.gui.SearchBoxWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;

public class SearchComponent {
    private final ScreenHandler menu;
    private final Inventory container;
    private final SearchBoxWidget searchBox;

    private String lastQuery;
    private CompletableFuture<Slot[]> lastSearch;
    private Slot[] lastResult;

    public SearchComponent(ScreenHandler menu, Inventory container, SearchBoxWidget searchBox) {
        this.menu = menu;
        this.container = container;
        this.searchBox = searchBox;
    }

    public void setSearchQuery(String query) {
        lastQuery = query;
        if (lastSearch != null && !lastSearch.isDone()) {
            lastSearch.cancel(false);
        }
        if (!StringUtils.isEmpty(query)) {
            lastSearch = ItemSearchHandler.searchAsync(menu, container, query);
            lastSearch.thenAcceptAsync(result -> lastResult = result, MinecraftClient.getInstance());
        }
        else {
            lastResult = null;
        }
    }

    public boolean hasCompletedSearch() {
        return lastResult != null;
    }

    public Slot[] getLastResult() {
        return lastResult;
    }

    public void slotClicked() {
        setSearchQuery(lastQuery); // pick up on any changes
        searchBox.setFocused(false);
    }

    public boolean keyPressed(int key, int j, int k) {
        // consume all key presses except for the escape key if the search bar is focused.
        if (searchBox.isFocused() && key != InputUtil.GLFW_KEY_ESCAPE) {
            searchBox.keyPressed(key, j, k);
            return true;
        }
        return false;
    }
}
