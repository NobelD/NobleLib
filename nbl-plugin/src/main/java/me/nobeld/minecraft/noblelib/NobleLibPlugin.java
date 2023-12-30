package me.nobeld.minecraft.noblelib;

import io.papermc.lib.PaperLib;
import org.bukkit.plugin.java.JavaPlugin;

public class NobleLibPlugin extends JavaPlugin {
    private static SubLibs lib;
    public static final boolean hasPaper = PaperLib.isPaper();
    @Override
    public void onEnable() {
        lib = new SubLibs(this);
    }
    @Override
    public void onDisable() {
        lib.getAdventure().disable();
    }
    public static SubLibs getLib() {
        return lib;
    }
}