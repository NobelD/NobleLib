package me.nobeld.minecraft.noblelib.adventure;

import me.nobeld.minecraft.noblelib.AdventureLib;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.nobeld.minecraft.noblelib.AdventureLib.hasPaper;

@SuppressWarnings("resource")
public class AdvBase {
    private final AdventureLib base;
    public AdvBase(AdventureLib base) {
        this.base = base;
    }
    public Audience audienceConsole() {
        if (hasPaper) return Bukkit.getConsoleSender();
        else return base.adventure().console();
    }
    public void consoleMsg(Component component) {
        if (isEmpty(component)) return;
        audienceConsole().sendMessage(component);
    }
    public Audience audiencePlayer(Player player) {
        if (hasPaper) return player;
        return base.adventure().player(player);
    }
    public void playerMsg(Player player, Component component) {
        if (isEmpty(component)) return;
        if (hasPaper) player.sendMessage(component);
        else base.adventure().player(player).sendMessage(component);
    }
    public Audience audienceSender(CommandSender sender) {
        if (hasPaper) return sender;
        else return base.adventure().sender(sender);
    }
    public void senderMsg(CommandSender sender, Component component) {
        if (isEmpty(component)) return;
        audienceSender(sender).sendMessage(component);
    }
    public Audience audienceAll() {
        if (hasPaper) {
            Audience players = Audience.audience(Bukkit.getOnlinePlayers());
            return Audience.audience(players, Bukkit.getConsoleSender());
        }
        else return base.adventure().all();
    }
    public void broadcast(Component component) {
        if (isEmpty(component)) return;
        if (hasPaper) Bukkit.broadcast(component);
        else base.adventure().all().sendMessage(component);
    }
    public Audience audienceWorld(World world) {
        return Audience.audience(world.getPlayers());
    }
    public Audience audienceWorldConsole(World world) {
        Audience audience = Audience.audience(world.getPlayers());
        if (hasPaper) {
            return Audience.audience(audience, Bukkit.getConsoleSender());
        } else {
            return Audience.audience(audience, base.adventure().console());
        }
    }
    public void worldMsg(World world, Component component) {
        if (isEmpty(component)) return;
        audienceWorld(world).sendMessage(component);
    }
    public void worldConsoleMsg(World world, Component component) {
        if (isEmpty(component)) return;
        audienceWorldConsole(world).sendMessage(component);
    }
    public Audience getAllPlayers() {
        if (hasPaper) return Audience.audience(Bukkit.getOnlinePlayers());
        else return base.adventure().players();
    }
    public void playersBroadcast(Component component) {
        if (isEmpty(component)) return;
        getAllPlayers().sendMessage(component);
    }
    public boolean isEmpty(Component component) {
        return component == null || component.equals(Component.empty());
    }
}
