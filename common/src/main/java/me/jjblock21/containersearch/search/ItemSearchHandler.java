package me.jjblock21.containersearch.search;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public final class ItemSearchHandler {
    public static CompletableFuture<Slot[]> searchAsync(ScreenHandler menu, Inventory targetContainer, String query) {
        if (StringUtils.isEmpty(query)) {
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> search(menu, targetContainer, query));
    }

    private static Slot[] search(ScreenHandler menu, Inventory targetContainer, String query) {
        // This allocates a new list every time and a bunch of SearchResults every time, but I will leave it like this for now.

        // Create a stream of remaining items to render from the item stream.
        return menu.slots.stream().filter(slot -> {
            if (slot.inventory == targetContainer) {
                return checkSlot(slot, query);
            }
            return true;
        }).toArray(Slot[]::new);
    }

    private static boolean checkSlot(Slot slot, String query) {
        ItemStack item = slot.getStack();

        if (item.isOf(Items.SHULKER_BOX)) {
            return checkShulkerBox(item, query);
        }
        return checkItem(item, query);
    }

    private static boolean checkShulkerBox(ItemStack item, String query) {
        NbtCompound metadata = BlockItem.getBlockEntityNbt(item);
        if (metadata == null || !metadata.contains("Items", 9))
            return false;

        NbtList itemTags = metadata.getList("Items", 10);
        Stream<ItemStack> items = itemTags.stream().map(item2 -> ItemStack.fromNbt((NbtCompound)item2));

        // Check if any items inside the shulker match the query.
        return items.anyMatch(item2 -> checkItem(item2, query));
    }

    private static boolean checkItem(ItemStack item, String query) {
        if (item == ItemStack.EMPTY)
            return false;

        if (item.isOf(Items.ENCHANTED_BOOK)) {
            NbtList enchantTags = EnchantedBookItem.getEnchantmentNbt(item);
            return enchantTags.stream().anyMatch(tag -> checkEnchantment((NbtCompound)tag, query));
        }

        return matchesQuery(item.getName().getString(), query);
    }

    private static boolean checkEnchantment(NbtCompound tag, String query) {
        Optional<Enchantment> enchantment = Registries.ENCHANTMENT.getOrEmpty(EnchantmentHelper.getIdFromNbt(tag));

        if (enchantment.isPresent()) {
            int level = EnchantmentHelper.getLevelFromNbt(tag);
            String name = getEnchantSearchName(enchantment.get(), level);

            return matchesQuery(name, query);
        }
        return false;
    }

    private static String getEnchantSearchName(Enchantment enchantment, int level) {
        MutableText component = Text.translatable(enchantment.getTranslationKey())
                .append(ScreenTexts.SPACE)
                .append(Integer.toString(level)); // This is so you can also search for specific levels.

        return component.getString();
    }

    private static boolean matchesQuery(String str, String query) {
        // Check if the name or a word preceded by a whitespace character starts with the search query.
        // This is done to avoid matching sequences inside of words which might create unwanted results.
        if (StringUtils.startsWithIgnoreCase(str, query))
            return true;

        return StringUtils.containsIgnoreCase(str, " " + query);
    }
}
