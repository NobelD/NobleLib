package me.nobeld.minecraft.noblelib;

import io.papermc.lib.PaperLib;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityItemLib {
    private static JavaPlugin plugin;
    public static final boolean hasPaper = PaperLib.isPaper();
    public static NamespacedKey pluginKey(String key) {
        key = key.replace(" ", "_");
        return new NamespacedKey(plugin, key);
    }
    public EntityItemLib(JavaPlugin plugin) {
        EntityItemLib.plugin = plugin;
    }
}
