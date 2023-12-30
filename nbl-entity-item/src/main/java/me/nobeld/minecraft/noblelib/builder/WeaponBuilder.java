package me.nobeld.minecraft.noblelib.builder;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class WeaponBuilder extends ItemBuilder {
    public WeaponBuilder(Material material, int amount) {
        super(material, amount);
    }
    public WeaponBuilder(Material material) {
        super(material);
    }
    public WeaponBuilder setAttributes(EquipmentSlot slot, double attackDamage, double attackSpeed) {
        ItemMeta meta = this.stack.getItemMeta();
        if (attackDamage >= 0) {
            AttributeModifier modDamage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", attackDamage, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modDamage);
        }
        if (attackSpeed >= 0) {
            AttributeModifier modSpeed = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", attackSpeed, AttributeModifier.Operation.ADD_NUMBER, slot);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modSpeed);
        }
        this.stack.setItemMeta(meta);
        return this;
    }
}
