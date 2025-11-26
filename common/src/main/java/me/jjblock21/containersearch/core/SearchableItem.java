package me.jjblock21.containersearch.core;

import net.minecraft.inventory.Inventory;
import java.util.function.Consumer;

/**
 * Required to call methods added by mixins from other classes
 */
public interface SearchableItem {
    void container_search$setModifyListener(Consumer<Inventory> listener);
    void container_search$setEnabled(boolean matching);
}
