package me.nobeld.minecraft.noblelib.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import me.nobeld.minecraft.noblelib.CommandLib;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.function.Function;

public abstract class BaseCommand {
    final Function<Command.Builder<CommandSender>, Command.Builder<CommandSender>> builder;
    public BaseCommand(Function<Command.Builder<CommandSender>, Command.Builder<CommandSender>> builder) {
        this.builder = builder;
    }
    public static void sendMsg(CommandContext<CommandSender> c, Component msg) {
        CommandSender sender = c.getSender();
        CommandLib.getAdventure().audienceSender(sender).sendMessage(msg);
    }
    abstract void register(CommandManager<CommandSender> mng, Command.Builder<CommandSender> builder);
    public Command.Builder<CommandSender> getCommand(Command.Builder<CommandSender> base) {
        return builder.apply(base);
    }
}
