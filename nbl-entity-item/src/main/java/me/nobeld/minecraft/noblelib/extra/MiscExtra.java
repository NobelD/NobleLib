package me.nobeld.minecraft.noblelib.extra;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MiscExtra {
    public static ArmorTrim createArmorTrim(NamespacedKey material, NamespacedKey pattern, TrimMaterial materialFallback, TrimPattern patternFallback) {
        TrimMaterial mat = Registry.TRIM_MATERIAL.get(material);
        TrimPattern pat = Registry.TRIM_PATTERN.get(pattern);
        if (mat == null) mat = materialFallback;
        if (pat == null) pat = patternFallback;
        return new ArmorTrim(mat,pat);
    }
    public static boolean existArmorTrim(NamespacedKey material, NamespacedKey pattern) {
        TrimMaterial mat = Registry.TRIM_MATERIAL.get(material);
        TrimPattern pat = Registry.TRIM_PATTERN.get(pattern);
        return (mat == null || pat == null);
    }
    @Nullable
    public static ArmorTrim getArmorTrim(NamespacedKey material, NamespacedKey pattern) {
        TrimMaterial mat = Registry.TRIM_MATERIAL.get(material);
        TrimPattern pat = Registry.TRIM_PATTERN.get(pattern);
        if (mat == null || pat == null) return null;
        return new ArmorTrim(mat,pat);
    }
    public static void dropItem(@NotNull Block block, @NotNull ItemStack item) {
        block.getWorld().dropItemNaturally(block.getLocation().add(0, 0.1, 0), item);
    }
    public static void dropItem(@NotNull Location location, @NotNull ItemStack item) {
        location.getWorld().dropItemNaturally(location.add(0, 0.1, 0), item);
    }
}
