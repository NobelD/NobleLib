package me.nobeld.minecraft.noblelib.builder;

import me.nobeld.minecraft.noblelib.extra.MobExtra;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

import static me.nobeld.minecraft.noblelib.adventure.MiniResolver.formatServer;

public class EntityBuilder<T extends LivingEntity> {
    protected T entity;
    protected EntityEquipment equipment;
    protected boolean custom = false;
    /** Create the builder using an entity. */
    public EntityBuilder(@NotNull T entity) {
        this.entity = entity;
        setData();
    }
    /** Create the builder using an entity class to spawn it. */
    public EntityBuilder(Class<T> clazz, Location loc) {
        this(loc.getWorld().spawn(loc, clazz));
    }
    /** Create the builder using an entity and removing another. */
    public EntityBuilder(LivingEntity oldEntity, T newEntity) {
        oldEntity.remove();
        this.entity = newEntity;
        setData();
    }
    /** Create the builder using an entity class and removing another. */
    public EntityBuilder(Class<T> clazz, LivingEntity oldEntity) {
        this(oldEntity, oldEntity.getWorld().spawn(oldEntity.getLocation(), clazz));
    }
    /** Create the builder with an entity and cancelling the spawn of another. */
    public EntityBuilder(T newEntity, CreatureSpawnEvent event) {
        event.setCancelled(true);
        this.entity = newEntity;
        setData();
    }
    /** Create the builder spawning an entity with their class and cancelling the spawn of the other. */
    public EntityBuilder(Class<T> clazz, CreatureSpawnEvent event) {
        this(event.getLocation().getWorld().spawn(event.getLocation(), clazz), event);
    }
    private void setData() {
        this.equipment = entity.getEquipment();
    }
    public EntityBuilder<T> setName(Component component) {
        if (component != null)
            MobExtra.setName(this.entity, component);
        return this;
    }
    public EntityBuilder<T> setName(String text) {
        if (text != null)
            MobExtra.setName(this.entity, formatServer(text));
        return this;
    }
    public EntityBuilder<T> setName(Component component, boolean show) {
        if (component != null)
            MobExtra.setName(this.entity, component);
        return showName(show);
    }
    public EntityBuilder<T> showName(boolean show) {
        this.entity.setCustomNameVisible(show);
        return this;
    }
    public EntityBuilder<T> setCollidable(boolean collidable) {
        entity.setCollidable(collidable);
        return this;
    }
    public EntityBuilder<T> setNaturalDespawn(boolean despawn) {
        entity.setRemoveWhenFarAway(despawn);
        return this;
    }
    public EntityBuilder<T> setAI(boolean ai) {
        entity.setAI(ai);
        return this;
    }
    public EntityBuilder<T> setInvulnerable(boolean invulnerable) {
        entity.setInvulnerable(invulnerable);
        return this;
    }
    public EntityBuilder<T> addEffect(PotionEffectType type, int duration, int level) {
        if (type != null)
            MobExtra.overrideEffect(this.entity, type, duration, level);
        return this;
    }
    public EntityBuilder<T> addEffect(PotionEffect effect) {
        if (effect != null)
            MobExtra.overrideEffect(this.entity, effect);
        return this;
    }
    public EntityBuilder<T> addEffectColl(Collection<PotionEffect> effect) {
        if (effect != null)
            MobExtra.addEffectColl(this.entity, effect);
        return this;
    }
    public EntityBuilder<T> addEffectRandom(List<PotionEffect> effectList) {
        if (effectList != null)
            MobExtra.addEffectRandom(entity, effectList, 0, effectList.size(), true);
        return this;
    }
    public EntityBuilder<T> overrideEffectRandom(List<PotionEffect> effectList) {
        if (effectList != null)
            MobExtra.overrideEffectRandom(entity, effectList, 0, effectList.size(), true);
        return this;
    }
    public EntityBuilder<T> overrideEffectRandom(List<PotionEffect> effectList, int min, int max) {
        if (min > max) min = max - 1;
        if (effectList != null)
            MobExtra.overrideEffectRandom(entity, effectList, min, max, min == max);
        return this;
    }
    public EntityBuilder<T> setSize(int size) {
        if (entity instanceof Slime slime) {
            slime.setSize(size);
        } else if (entity instanceof Phantom phantom) {
            phantom.setSize(size);
        }
        return this;
    }
    public EntityBuilder<T> setAnger(int anger) {
        if (anger < 0) anger = Integer.MAX_VALUE;
        if (entity instanceof Bee bee) {
            bee.setAnger(anger);
        } else if (entity instanceof PigZombie pigman) {
            pigman.setAnger(anger);
        }
        return this;
    }
    public EntityBuilder<T> setPassenger(LivingEntity passenger) {
        if (passenger != null)
            entity.addPassenger(passenger);
        return this;
    }
    public EntityBuilder<T> setOnlyMaxHealth(double amount) {
        AttributeInstance inst = this.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (inst != null) {
            inst.setBaseValue(amount);
        }
        return this;
    }
    public EntityBuilder<T> setOnlyHealth(double amount) {
        this.entity.setHealth(amount);
        return this;
    }
    public EntityBuilder<T> setHealth(double amount) {
        AttributeInstance inst = this.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (inst != null) {
            inst.setBaseValue(amount);
            this.entity.setHealth(amount);
        }
        return this;
    }
    public EntityBuilder<T> multiplyMaxHealth(double amount) {
        AttributeInstance inst = this.entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (inst != null) {
            double newamount = inst.getBaseValue() * amount;
            inst.setBaseValue(newamount);
            this.entity.setHealth(newamount);
        }
        return this;
    }
    public EntityBuilder<T> setAttackDamage(double amount) {
        AttributeInstance inst = this.entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (inst != null) inst.setBaseValue(amount);
        return this;
    }
    public EntityBuilder<T> addPersistent(NamespacedKey key) {
        this.entity.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        return this;
    }
    public EntityBuilder<T> setExplosion(float power) {
        if (entity instanceof Creeper creeper) {
            creeper.setExplosionRadius((int) power);
        } else if (entity instanceof Ghast ghast) {
            ghast.setExplosionPower((int) power);
        } else if (entity instanceof Fireball fireball) {
            fireball.setYield(power);
        } else if (entity instanceof TNTPrimed tnt) {
            tnt.setYield(power);
        }
        return this;
    }
    public EntityBuilder<T> setExplosion(int power) {
        return setExplosion((float) power);
    }
    public EntityBuilder<T> setCharged() {
        if (entity instanceof Creeper creeper) {
            creeper.setPowered(true);
        }
        return this;
    }
    public EntityBuilder<T> setFuse(int fuse) {
        if (entity instanceof Creeper creeper) {
            creeper.setMaxFuseTicks(fuse);
        }
        return this;
    }
    public EntityBuilder<T> setFuseMultiply(double fuse) {
        if (entity instanceof Creeper creeper) {
            creeper.setMaxFuseTicks((int) (creeper.getMaxFuseTicks() * fuse));
        }
        return this;
    }
    public EntityBuilder<T> setArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        setData();
        if (equipment != null)
            MobExtra.setArmor(equipment, helmet, chestplate, leggings, boots);
        return this;
    }
    public EntityBuilder<T> setArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, Enchantment enchant, int level) {
        if (equipment != null)
            MobExtra.setArmor(equipment, helmet, chestplate, leggings, boots, enchant, level);
        return this;
    }
    public EntityBuilder<T> setMainHand(ItemStack item) {
        setData();
        if (equipment != null && item != null)
            equipment.setItemInMainHand(item);
        return this;
    }
    public EntityBuilder<T> setOffHand(ItemStack item) {
        setData();
        if (equipment != null && item != null)
            equipment.setItemInOffHand(item);
        return this;
    }
    public EntityBuilder<T> setDropChances(float armorChance, float mainChance, float offChance) {
        setData();
        if (equipment != null)
            MobExtra.setDropChances(equipment, armorChance, mainChance, offChance);
        return this;
    }
    public EntityBuilder<T> armorDropChance(float armorChance) {
        setData();
        if (equipment != null)
            MobExtra.setArmorChances(equipment, armorChance);
        return this;
    }
    public EntityBuilder<T> handsDropChance(float mainChance, float offChance) {
        setData();
        if (equipment != null)
            MobExtra.setHandsChances(equipment, mainChance, offChance);
        return this;
    }
    public T build() {
        return this.entity;
    }
}
