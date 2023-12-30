package me.nobeld.minecraft.noblelib.model;

import me.nobeld.minecraft.noblelib.builder.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class ItemModel {
    private ItemStack item;
    private Component name;
    private NamespacedKey key;
    public ItemModel(ItemStack item, Component itemName, NamespacedKey key) {
        this.item = item;
        this.name = itemName;
        this.key = key;
    }
    public ItemStack getItem() {
        return item;
    }
    public void setItem(ItemStack item) {
        this.item = item;
    }
    public Component getName() {
        return name;
    }
    public void setName(Component name) {
        this.name = name;
    }
    public NamespacedKey getKey() {
        return key;
    }
    public void setKey(NamespacedKey key) {
        this.key = key;
    }
    public ItemBuilder getBuilder() {
        return new ItemBuilder(item);
    }
}
