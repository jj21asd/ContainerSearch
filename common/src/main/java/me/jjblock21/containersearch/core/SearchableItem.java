package me.jjblock21.containersearch.core;

import net.minecraft.inventory.Inventory;

import java.util.function.Consumer;

public interface SearchableItem {
    void container_search$setModifyListener(Consumer<Inventory> listener);
    void container_search$setMatching(boolean matching);
}
