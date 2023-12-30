package me.nobeld.minecraft.noblelib.dragon;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;

public abstract class DragonAttack {
    private final String name;
    private int cooldown;
    private boolean executed;
    private final int defCooldown;
    private Location location;
    private World world;
    private final SecureRandom random = new SecureRandom();
    private DragonData dragonData;
    public DragonAttack(@NotNull String name, @Nullable Location location) {
        this(name, location, 5);
    }
    public DragonAttack(@NotNull String name, @Nullable Location location, @NotNull Integer defCooldown) {
        this.name = name.replace(" ", "_");
        this.defCooldown = defCooldown;
        this.cooldown = defCooldown;
        this.executed = false;
        this.dragonData = null;
        this.location = location;
        if (location != null) this.world = location.getWorld();
    }
    public void setDragonData(DragonData data) {
        this.dragonData = data;
    }
    public DragonData getDragonData() {
        return this.dragonData;
    }
    public String name() {
        return name;
    }
    public int duration() {
        return cooldown;
    }
    public void setDuration() {
        this.cooldown = defCooldown;
    }
    public void setDuration(int duration) {
        this.cooldown = duration;
    }
    public boolean active() {
        return executed;
    }
    public void toggle(boolean active) {
        this.executed = active;
    }
    public void tick() {
        this.cooldown -= 1;
    }
    public void setLocation(Location location) {
        this.location = location;
        if (location == null) return;
        this.world = location.getWorld();
    }
    public World world() {
        return world;
    }
    public Location location() {
        return location;
    }
    public SecureRandom random() {return random;}
    public abstract void onStart();
    public abstract void onTick();
    public abstract void onEnd();
}
