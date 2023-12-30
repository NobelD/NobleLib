package me.nobeld.minecraft.noblelib;

import org.bukkit.plugin.java.JavaPlugin;

public class SubLibs {
    private final AdventureLib adventure;
    private final EntityItemLib entityItem;
    private final ModelLib modelLib;
    public SubLibs(JavaPlugin plugin) {
        adventure = new AdventureLib(plugin);
        entityItem = new EntityItemLib(plugin);
        modelLib = new ModelLib(plugin);
    }
    public AdventureLib getAdventure() {
        return adventure;
    }
    public EntityItemLib getEntityItem() {
        return entityItem;
    }
    public ModelLib getModelLib() {
        return modelLib;
    }
}
