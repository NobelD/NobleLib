package me.nobeld.minecraft.noblelib;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("InstantiationOfUtilityClass")
public class SubLibs {
    private final AdventureLib adventure;
    private final EntityItemLib entityItem;
    private final ModelLib modelLib;
    private final CommandLib commandLib;
    public SubLibs(JavaPlugin plugin) {
        adventure = new AdventureLib(plugin) {
            @Override
            public String getPrefix() {
                return "";
            }
            @Override
            public boolean usePrefix() {
                return false;
            }
        };
        entityItem = new EntityItemLib(plugin);
        modelLib = new ModelLib(plugin);
        commandLib = new CommandLib(adventure.getMethods());
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
    public CommandLib getCommandLib() {
        return commandLib;
    }
}
