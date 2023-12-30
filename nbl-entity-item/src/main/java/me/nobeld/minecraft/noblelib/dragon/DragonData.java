package me.nobeld.minecraft.noblelib.dragon;

import java.util.Objects;

public class DragonData {
    private DragonPhase phase;
    private DragonAttack chosen;
    private String last;
    private boolean canAttack;
    private int cooldown;
    public DragonData(DragonPhase phase, DragonAttack chosen, String last, boolean canAttack, int cooldown) {
        this.phase = phase;
        this.chosen = chosen;
        this.last = last;
        this.canAttack = canAttack;
        this.cooldown = cooldown;
    }
    public void noneAttack() {
        this.chosen = new NoneAttack();
    }
    public boolean tickCooldown() {
        if (cooldown <= 0) return true;
        cooldown -= 1;
        return false;
    }
    public DragonPhase getPhase() {
        return this.phase;
    }
    public void setPhase(DragonPhase phase) {
        this.phase = phase;
    }
    public DragonAttack getChosen() {
        return this.chosen;
    }
    public void setChosen(DragonAttack chosen) {
        this.chosen = chosen;
    }
    public String getLast() {
        return this.last;
    }
    public void setLast(String last) {
        this.last = last;
    }
    public boolean isLast(String type) {
        return Objects.equals(this.last, type);
    }
    public boolean canAttack() {
        return this.canAttack;
    }
    public void canAttack(boolean b) {
        this.canAttack = b;
    }
    public int getCooldown() {
        return this.cooldown;
    }
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
