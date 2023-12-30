package me.nobeld.minecraft.noblelib.model;

import me.nobeld.minecraft.noblelib.builder.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;

public class OptionsItemModel extends ItemModel {
    public OptionsItemModel(ItemStack item, Component name, NamespacedKey key,
                            ExtraOptions options,
                            Function<ItemBuilder, ItemBuilder> extra,
                            Map<@NotNull Enchantment, @NotNull Integer> enchants,
                            ItemStack clone) {
        super(resolveItem(item, options, extra, enchants, clone), name, key);
    }
    private static ItemStack resolveItem(ItemStack stack, ExtraOptions options, Function<ItemBuilder, ItemBuilder> extra, Map<@NotNull Enchantment, @NotNull Integer> enchants, @Nullable ItemStack clone) {
        ItemBuilder builder;
        if (extra != null && options.extra()) {
            builder = extra.apply(new ItemBuilder(stack));
        } else {
            builder = new ItemBuilder(stack);
        }

        if (options.allEnchants() && enchants != null) {
            for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                builder.addEnchantment(entry.getKey(), entry.getValue());
            }
        }
        if (!options.allEnchants() && options.cloneEnchants() && clone != null) {
            try {
                ItemStack item = builder.build();
                if (!item.getEnchantments().isEmpty()) {
                    item.addEnchantments(clone.getEnchantments());
                }
                builder = new ItemBuilder(item);
            } catch (IllegalArgumentException ignored) {
            }
        }

        return builder.build();
    }
}
