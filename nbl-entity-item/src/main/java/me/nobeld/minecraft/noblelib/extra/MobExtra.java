package me.nobeld.minecraft.noblelib.extra;

import me.nobeld.minecraft.noblelib.builder.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static me.nobeld.minecraft.noblelib.EntityItemLib.hasPaper;
import static me.nobeld.minecraft.noblelib.adventure.MiniResolver.formatMini;
import static me.nobeld.minecraft.noblelib.adventure.MiniResolver.getLegacy;

public class MobExtra {
    private final static SecureRandom random = new SecureRandom();
    @SuppressWarnings("deprecation")
    public static void setName(Entity entity , Component component) {
        if (hasPaper) entity.customName(component);
        else entity.setCustomName(getLegacy(component));
    }
    public static void breakBlocks(LivingEntity entity, int range, Material toBreak, Material toReplace, Sound sound, float volumen, float pitch) {
        List<Block> listBlock = entity.getLineOfSight(null, range);
        for (Block block : listBlock) {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 1; j++) {
                    for (int k = -1; k < 1; k++) {
                        Block block2 = block.getRelative(i, j, k);
                        if (block2.getType() != toBreak) continue;
                        block2.setType(toReplace);
                        block2.getWorld().playSound(block2.getLocation(), sound, volumen, pitch);
                    }
                }
            }
        }
    }
    public static void setName(Entity entity , String text) {
        Component component = formatMini(text);
        setName(entity, component);
    }
    public static boolean isEntityLimit(Location loc, int radius, int limit, Class<? extends LivingEntity> clazz) {
        return loc.getWorld().getNearbyEntitiesByType(clazz, loc, radius).size() >= limit;
    }
    public static void addEffect(LivingEntity entity, PotionEffect eff) {
        entity.addPotionEffect(eff);
    }
    public static void addEffect(LivingEntity entity, PotionEffectType type, int duration, int level) {
        if (duration < 0) duration = PotionEffect.INFINITE_DURATION;
        entity.addPotionEffect(new PotionEffect(type, duration, level - 1));
    }
    public static void addEffectColl(LivingEntity entity, Collection<PotionEffect> collection) {
        entity.addPotionEffects(collection);
    }
    public static void overrideEffect(LivingEntity entity, PotionEffect effect) {
        if (entity.hasPotionEffect(effect.getType())) entity.removePotionEffect(effect.getType());
        addEffect(entity, effect);
    }
    public static void overrideEffectColl(LivingEntity entity, Collection<PotionEffect> collection) {
        for (PotionEffect eff : collection) {
            if (entity.hasPotionEffect(eff.getType())) entity.removePotionEffect(eff.getType());
        }
        addEffectColl(entity, collection);
    }
    public static void overrideEffect(LivingEntity entity, PotionEffectType type, int duration, int level) {
        if (entity.hasPotionEffect(type)) entity.removePotionEffect(type);
        addEffect(entity ,type, duration, level - 1);
    }
    public static void addEffectRandom(LivingEntity entity, List<PotionEffect> list, int min, int max, boolean onlyMax) {
        if (list.size() < max) max = list.size();
        if (min > max) min = max - 1;
        if (min == max) onlyMax = true;
        int times = random.nextInt(min, max + 1);
        if (onlyMax) times = max;

        List<PotionEffect> effs = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            int index = random.nextInt(list.size());
            effs.add(list.get(index));
            list.remove(index);
        }
        addEffectColl(entity, effs);
    }
    public static void overrideEffectRandom(LivingEntity entity, List<PotionEffect> list, int min, int max, boolean onlyMax) {
        if (list.size() < max) max = list.size();
        if (min > max) min = max - 1;
        if (min == max) onlyMax = true;
        int times = random.nextInt(min, max + 1);
        if (onlyMax) times = max;

        List<PotionEffect> effs = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            int index = random.nextInt(list.size());
            effs.add(list.get(index));
            list.remove(index);
        }
        overrideEffectColl(entity, effs);
    }
    /**
     * Create a PotionEffect using common variables.
     * @param type The effect type.
     * @param duration The duration in ticks, if the duration is negative will be infinite.
     * @param level The effect level
     * @return The created Potion Effect.
     */
    public static PotionEffect createEff(PotionEffectType type, int duration, int level) {
        int dur = duration < 0 ? PotionEffect.INFINITE_DURATION : duration;
        int lvl = level <= 0 ? 1 : level - 1;
        return new PotionEffect(type,  dur, lvl);
    }
    public static void setArmor(EntityEquipment equipment, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        if (helmet != null) equipment.setHelmet(helmet);
        if (chestplate != null) equipment.setChestplate(chestplate);
        if (leggings != null) equipment.setLeggings(leggings);
        if (boots != null) equipment.setBoots(boots);
    }
    public static void setArmor(EntityEquipment equipment, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, Enchantment enchant, int level) {
        if (enchant == null) {
            setArmor(equipment, helmet, chestplate, leggings, boots);
            return;
        }
        if (helmet != null) equipment.setHelmet(new ItemBuilder(helmet).addEnchantment(enchant, level).build());
        if (chestplate != null) equipment.setChestplate(new ItemBuilder(chestplate).addEnchantment(enchant, level).build());
        if (leggings != null) equipment.setLeggings(new ItemBuilder(leggings).addEnchantment(enchant, level).build());
        if (boots != null) equipment.setBoots(new ItemBuilder(boots).addEnchantment(enchant, level).build());
    }
    public static void setMainHand(EntityEquipment equipment, ItemStack main) {
        if (main != null) equipment.setItemInMainHand(main);
    }
    public static void setMainHand(EntityEquipment equipment, ItemStack main, Enchantment enchant, int lvl) {
        if (enchant == null) setMainHand(equipment, main);
        else equipment.setItemInMainHand(new ItemBuilder(main).addEnchantment(enchant, lvl).build());
    }
    public static void setOffHand(EntityEquipment equipment, ItemStack off) {
        if (off != null) equipment.setItemInOffHand(off);
    }
    public static void setOffHand(EntityEquipment equipment, ItemStack off, Enchantment enchant, int lvl) {
        if (off == null) return;
        if (enchant == null) setOffHand(equipment, off);
        else equipment.setItemInOffHand(new ItemBuilder(off).addEnchantment(enchant, lvl).build());
    }
    public static void setDropChances(EntityEquipment equipment, float armorChance, float mainChance, float offChance) {
        setArmorChances(equipment, armorChance);
        setHandsChances(equipment, mainChance, offChance);
    }
    public static void setArmorChances(EntityEquipment equipment, float armorChance) {
        equipment.setHelmetDropChance(armorChance);
        equipment.setChestplateDropChance(armorChance);
        equipment.setLeggingsDropChance(armorChance);
        equipment.setBootsDropChance(armorChance);
    }
    public static void setHandsChances(EntityEquipment equipment, float mainChance, float offChance) {
        equipment.setItemInMainHandDropChance(mainChance);
        equipment.setItemInOffHandDropChance(offChance);
    }
}
