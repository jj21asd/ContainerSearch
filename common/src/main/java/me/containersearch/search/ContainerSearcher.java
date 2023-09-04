package me.containersearch.search;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
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

        if (item.is(Items.SHULKER_BOX)) {
            return checkShulkerBox(item, query);
        }
        return checkItem(item, query);
    }

    private static boolean checkShulkerBox(ItemStack item, String query) {
        CompoundTag metadata = BlockItem.getBlockEntityData(item);
        if (metadata == null || !metadata.contains("Items", 9))
            return false;

        ListTag itemTags = metadata.getList("Items", 10);
        Stream<ItemStack> items = itemTags.stream().map(item2 -> ItemStack.of((CompoundTag)item2));

        // Check if any items inside the shulker match the query.
        return items.anyMatch(item2 -> checkItem(item2, query));
    }

    private static boolean checkItem(ItemStack item, String query) {
        if (item == ItemStack.EMPTY)
            return false;

        if (item.is(Items.ENCHANTED_BOOK)) {
            ListTag enchantTags = EnchantedBookItem.getEnchantments(item);
            return enchantTags.stream().anyMatch(tag -> checkEnchantment((CompoundTag)tag, query));
        }

        return matchesQuery(item.getHoverName().getString(), query);
    }

    private static boolean checkEnchantment(CompoundTag tag, String query) {
        Optional<Enchantment> enchantment = BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(tag));

        if (enchantment.isPresent()) {
            int level = EnchantmentHelper.getEnchantmentLevel(tag);
            String name = getEnchantSearchName(enchantment.get(), level);

            return matchesQuery(name, query);
        }
        return false;
    }

    private static String getEnchantSearchName(Enchantment enchantment, int level) {
        MutableComponent component = Component.translatable(enchantment.getDescriptionId())
                .append(CommonComponents.SPACE)
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
