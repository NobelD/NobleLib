package me.nobeld.minecraft.noblelib;

import io.papermc.lib.PaperLib;
import me.nobeld.minecraft.noblelib.adventure.AdvBase;
import me.nobeld.minecraft.noblelib.adventure.MiniResolver;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AdventureLib {
    private final JavaPlugin plugin;
    private BukkitAudiences adventure;
    public static boolean hasPlaceholderAPI = hasClass("me.clip.placeholderapi.PlaceholderAPI");
    private final AdvBase methods;
    private final MiniResolver resolvers;
    public static final boolean hasPaper = PaperLib.isPaper();
    public AdventureLib(JavaPlugin plugin) {
        this.plugin = plugin;
        enable();
        methods = new AdvBase(this);
        resolvers = new MiniResolver(this);
    }
    public @NotNull BukkitAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }
    public void enable() {
        adventure = BukkitAudiences.create(plugin);
    }
    public void disable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }
    public static boolean hasClass(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }
    public AdvBase getMethods() {
        return methods;
    }
    public abstract String getPrefix();
    public abstract boolean usePrefix();
    public MiniResolver getResolvers() {
        return resolvers;
    }
}