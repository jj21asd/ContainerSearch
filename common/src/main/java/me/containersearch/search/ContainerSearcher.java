package me.containersearch.search;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.apache.commons.lang3.StringUtils;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public final class ContainerSearcher {
    public static CompletableFuture<Slot[]> searchAsync(AbstractContainerMenu menu, Container targetContainer, String query) {
        if (StringUtils.isEmpty(query)) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> search(menu, targetContainer, query));
    }

    private static Slot[] search(AbstractContainerMenu menu, Container targetContainer, String query) {
        // This allocates a new list every time and a bunch of SearchResults every time, but I will leave it like this for now.

        // Create a stream of remaining items to render from the item stream.
        return menu.slots.stream().filter(slot -> {
            if (slot.container == targetContainer) {
                return checkSlot(slot, query);
            }
            return true;
        }).toArray(Slot[]::new);
    }

    private static boolean checkSlot(Slot slot, String query) {
        ItemStack item = slot.getItem();

        if (isShulkerBox(item)) {
            // Always return here, don't show shulkers if their name matches to avoid confusion.
            return checkShulkerBox(item, query);
        }
        return checkItem(item, query);
    }

    private static boolean checkShulkerBox(ItemStack item, String query) {
        CompoundTag metadata = BlockItem.getBlockEntityData(item);
        if (metadata == null || !metadata.contains("Items", 9)) return false;

        ListTag itemTags = metadata.getList("Items", 10);
        Stream<ItemStack> items = itemTags.stream().map(item2 -> ItemStack.of((CompoundTag)item2));
        // Check if any items inside the shulker match the query.
        return items.anyMatch(item2 -> checkItem(item2, query));
    }

    private static boolean isShulkerBox(ItemStack item) {
        // First check if the item is a shulker box item.
        if (item.getItem() instanceof BlockItem) {
            Block block = ((BlockItem) item.getItem()).getBlock();
            return block instanceof ShulkerBoxBlock;
        }
        return false;
    }

    private static boolean checkItem(ItemStack item, String query) {
        if (item == ItemStack.EMPTY) return false;
        return matchesQuery(item.getHoverName().getString(), query);
    }

    private static boolean matchesQuery(String str, String query) {
        // Check if the name or a word preceded by a whitespace character starts with the search query.
        // This is done to avoid matching sequences inside of words which might create unwanted results.
        if (StringUtils.startsWithIgnoreCase(str, query))
            return true;

        return StringUtils.containsIgnoreCase(str, " " + query);
    }
}
