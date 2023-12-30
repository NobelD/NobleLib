package me.nobeld.minecraft.noblelib;

import me.nobeld.minecraft.noblelib.other.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ModelLib {
    private static JavaPlugin plugin;
    public ModelLib(JavaPlugin plugin) {
        ModelLib.plugin = plugin;
    }
    public static JavaPlugin getPlugin() {
        return plugin;
    }
    public static void startMetrics(JavaPlugin plugin, int id) {
        Metrics metrics = new Metrics(plugin, id);
        metrics.addCustomChart(new Metrics.MultiLineChart("players_and_servers", () -> {
            Map<String, Integer> valueMap = new HashMap<>();
            valueMap.put("servers", 1);
            valueMap.put("players", Bukkit.getOnlinePlayers().size());
            return valueMap;
        }));
    }
}