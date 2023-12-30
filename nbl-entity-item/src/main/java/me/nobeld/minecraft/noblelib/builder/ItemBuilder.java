package me.nobeld.minecraft.noblelib.builder;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import static me.nobeld.minecraft.noblelib.EntityItemLib.hasPaper;
import static me.nobeld.minecraft.noblelib.EntityItemLib.pluginKey;
import static me.nobeld.minecraft.noblelib.adventure.MiniResolver.getLegacy;

@SuppressWarnings("deprecation")
public class ItemBuilder {
    protected ItemStack stack;
    protected ItemMeta meta;
    protected Damageable damage;
    public ItemBuilder(ItemStack itemStack) {
        stack = new ItemStack(itemStack);
        setData();
    }
    public ItemBuilder(Material material) {
        stack = new ItemStack(material);
        setData();
    }
    public ItemBuilder(Material material, int amount) {
        stack = new ItemStack(material, amount);
        setData();
    }
    public ItemBuilder setLore(ArrayList<Component> list) {
        setData();
        if (hasPaper)
            meta.lore(list);
        else {
            ArrayList<String> newList = new ArrayList<>();
            list.forEach(c -> newList.add(getLegacy(c)));
            meta.setLore(newList);
        }
        stack.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setLore(Component lore) {
        if (lore == null) return this;
        setData();
        if (hasPaper)
            meta.lore(Collections.singletonList(lore));
        else meta.setLore(Collections.singletonList(getLegacy(lore)));
        stack.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setDurability(int durability) {
        setData();
        damage.setDamage(durability);
        stack.setItemMeta(damage);
        return this;
    }
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        setData();
        meta.setUnbreakable(unbreakable);
        stack.setItemMeta(this.meta);
        return this;
    }
    public ItemBuilder setModelData(int model) {
        setData();
        meta.setCustomModelData(model);
        stack.setItemMeta(this.meta);
        return this;
    }
    @SuppressWarnings("deprecation")
    public ItemBuilder setDisplayName(Component name) {
        if (name == null) return this;
        setData();
        if (hasPaper)
            meta.displayName(name);
        else
            meta.setDisplayName(getLegacy(name));
        stack.setItemMeta(this.meta);
        return this;
    }
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        if (enchantment == null) return this;
        setData();
        meta.addEnchant(enchantment, level, true);
        stack.setItemMeta(this.meta);
        return this;
    }
    public ItemBuilder addItemFlag(ItemFlag itemflag) {
        if (itemflag == null) return this;
        setData();
        meta.addItemFlags(itemflag);
        stack.setItemMeta(this.meta);
        return this;
    }
    public ItemBuilder setSingleStack() {
        setData();
        meta.getPersistentDataContainer().set(pluginKey("random"), PersistentDataType.STRING, String.valueOf(UUID.randomUUID()));
        stack.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setPersistentData(NamespacedKey key) {
        setData();
        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        stack.setItemMeta(meta);
        return this;
    }
    public <T> ItemBuilder setPersistentData(NamespacedKey key, PersistentDataType<?, T> type, T value) {
        setData();
        meta.getPersistentDataContainer().set(key, type, value);
        stack.setItemMeta(meta);
        return this;
    }
    public ItemStack build() {
        return this.stack;
    }
    void setData() {
        meta = stack.getItemMeta();
        if (stack instanceof Damageable dmg) {
            this.damage = dmg;
        }
    }
}
