package me.containersearch.search;

import com.mojang.blaze3d.platform.InputConstants;
import me.containersearch.gui.SearchBox;
import net.minecraft.client.Minecraft;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;

public class ContainerSearchHandler {
    private final AbstractContainerMenu menu;
    private final Container container;
    private final SearchBox searchBox;

    private String lastQuery;
    private CompletableFuture<Slot[]> lastSearch;
    private Slot[] lastResult;

    public ContainerSearchHandler(AbstractContainerMenu menu, Container container, SearchBox seachBox) {
        this.menu = menu;
        this.container = container;
        this.searchBox = seachBox;
    }

    public void setSearchQuery(String query) {
        lastQuery = query;
        if (lastSearch != null && !lastSearch.isDone()) {
            lastSearch.cancel(false);
        }
        if (!StringUtils.isEmpty(query)) {
            lastSearch = ContainerSearcher.searchAsync(menu, container, query);
            lastSearch.thenAcceptAsync(result -> lastResult = result, Minecraft.getInstance());
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
        // Repeat the last search to pick up on any added or removed items.
        setSearchQuery(lastQuery);

        searchBox.setFocused(false);
    }

    public boolean keyPressed(int key, int j, int k) {
        // Consume all key presses except for the escape key if the search bar is focused.
        if (searchBox.isFocused() && key != InputConstants.KEY_ESCAPE) {
            searchBox.keyPressed(key, j, k);
            return true;
        }
        return false;
    }
}
