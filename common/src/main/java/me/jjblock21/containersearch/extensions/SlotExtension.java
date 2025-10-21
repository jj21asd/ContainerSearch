package me.jjblock21.containersearch.extensions;

import net.minecraft.inventory.Inventory;

import java.util.function.Consumer;

public interface SlotExtension {
    void container_search$setModifyListener(Consumer<Inventory> listener);
    void container_search$setMatching(boolean matching);
}
