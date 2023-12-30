package me.nobeld.minecraft.noblelib.builder;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;

import java.util.UUID;

public class ArmorBuilder extends ItemBuilder {
    private ArmorMeta aMeta;
    private LeatherArmorMeta lMeta;
    public ArmorBuilder(Material material, int amount) {
        super(material, amount);
        setArmorData();
    }
    public ArmorBuilder(Material material) {
        super(material);
        setArmorData();
    }
    public ArmorBuilder setTrim(ArmorTrim trim) {
        setArmorData();
        aMeta.setTrim(trim);
        stack.setItemMeta(aMeta);
        return this;
    }
    public ArmorBuilder setColor(Color color) {
        setArmorData();
        lMeta.setColor(color);
        stack.setItemMeta(lMeta);
        return this;
    }
    public ArmorBuilder addAttributes(EquipmentSlot slot, int armor, int toughness, double knockback) {
        setData();
        if (armor >= 0) {
            AttributeModifier modArmor = new AttributeModifier(UUID.randomUUID(), "generic.armor", armor, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modArmor);
        }

        if (toughness >= 0) {
            AttributeModifier modToughness = new AttributeModifier(UUID.randomUUID(), "generic.armorToughness", toughness, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, modToughness);
        }

        if (knockback >= 0){
            AttributeModifier modKnockback = new AttributeModifier(UUID.randomUUID(), "generic.knockbackResistance", knockback, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, modKnockback);
        }

        stack.setItemMeta(meta);
        return this;
    }
    void setArmorData() {
        try {
            aMeta = (ArmorMeta) meta;
        } catch (Exception ignored) {
            aMeta = null;
        }
        try {
            lMeta = (LeatherArmorMeta) meta;
        } catch (Exception ignored) {
            lMeta = null;
        }
    }
}
