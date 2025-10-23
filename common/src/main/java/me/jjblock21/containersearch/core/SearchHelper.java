package me.jjblock21.containersearch.core;

import me.jjblock21.containersearch.ConfigModel;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import java.util.Optional;

public class SearchHelper {
    public static Query preprocessQuery(String queryText) {
        String[] tokens = tokenize(queryText);
        boolean containsNumbers = false;
        for (String token : tokens) {
            if (StringUtils.isNumeric(token)) {
                containsNumbers = true;
                break;
            }
        }
        return new Query(tokens, containsNumbers);
    }

    private static String[] tokenize(String text) {
        return StringUtils.split(text.trim().toLowerCase(), ' ');
    }

    public static boolean matchesItem(Query query, ItemStack stack) {
        if (stack.isEmpty()) return false;

        if (ConfigModel.searchShulkers && isShulkerBox(stack)) {
            NbtCompound nbt = BlockItem.getBlockEntityNbt(stack);
            if (matchesContents(query, nbt)) return true;
        }
        // TODO: Actually test if this works
        else if (ConfigModel.searchBundles && stack.getItem() instanceof BundleItem) {
            NbtCompound nbt = stack.getOrCreateNbt();
            if (matchesContents(query, nbt)) return true;
        }
        else if (stack.isOf(Items.ENCHANTED_BOOK)) {
            if (matchesEnchantments(query, stack)) return true;
        }
        else if (stack.isOf(Items.POTION)) {
            if (matchesEffects(query, stack)) return true;
        }

        if (stack.hasCustomName()) {
            // try the original name if the item was renamed
            if (matchesName(query, stack.getItem().getName().getString())) {
                return true;
            }
        }

        return matchesName(query, stack.getName().getString());
    }

    private static boolean isShulkerBox(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof BlockItem &&
            ((BlockItem)item).getBlock() instanceof ShulkerBoxBlock;
    }

    private static boolean matchesContents(Query query, NbtCompound nbt) {
        if (nbt != null && nbt.contains("Items")) {
            NbtList items = nbt.getList("Items", NbtList.COMPOUND_TYPE);

            for (int i = 0; i < items.size(); i++){
                ItemStack stack = ItemStack.fromNbt(items.getCompound(i));
                if (matchesItem(query, stack)) return true;
            }
        }
        return false;
    }

    private static boolean matchesEnchantments(Query query, ItemStack book) {
        NbtList enchantments = EnchantedBookItem.getEnchantmentNbt(book);
        for (int i = 0; i < enchantments.size(); i++) {
            NbtCompound nbt = enchantments.getCompound(i);

            Identifier id = EnchantmentHelper.getIdFromNbt(nbt);
            Optional<Enchantment> opt = Registries.ENCHANTMENT.getOrEmpty(id);
            if (opt.isPresent()) {
                String name = Text.translatable(opt.get().getTranslationKey()).getString();
                int level = EnchantmentHelper.getLevelFromNbt(nbt);

                if (matchesSpell(query, name, level))
                    return true;
            }
        }
        return false;
    }

    private static boolean matchesEffects(Query query, ItemStack potion) {
        for (StatusEffectInstance effect : PotionUtil.getPotionEffects(potion)) {
            String name = Text.translatable(effect.getTranslationKey()).getString();
            int level = effect.getAmplifier() + 1; // 0 = default potency

            if (matchesSpell(query, name, level))
                return true;
        }
        return false;
    }

    private static boolean matchesSpell(Query query, String name, int level) {
        if (!ConfigModel.interpretNumbersAsLevels) {
            return matchesName(query, name) || matchesLevel(query, level);
        }

        // if only one is specified (potential name/level) search for it only, otherwise search for both in combination.
        // this reduces unwanted results when searching for specific enchantments
        if (query.containsNumbers && query.tokens.length == 1) {
            return matchesLevel(query, level);
        } else if (query.containsNumbers) {
            return matchesName(query, name) && matchesLevel(query, level);
        } else {
            return matchesName(query, name);
        }
    }

    private static boolean matchesLevel(Query query, int level) {
        String levelStr = Integer.toString(level);
        for (String token : query.tokens) {
            if (levelStr.startsWith(token)) return true;
        }
        return false;
    }

    private static boolean matchesName(Query query, String name) {
        for (String token : tokenize(name)) {
            for (String token1 : query.tokens) {
                if (token.startsWith(token1)) return true;
            }
        }
        return false;
    }

    public record Query(String[] tokens, boolean containsNumbers) {}
}
