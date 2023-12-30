package me.nobeld.minecraft.noblelib;

import me.nobeld.minecraft.noblelib.adventure.AdvBase;

public class CommandLib {
    private static AdvBase adventure;
    public CommandLib(AdvBase adventure) {
        CommandLib.adventure = adventure;
    }
    public static AdvBase getAdventure() {
        return adventure;
    }
}