package me.nobeld.minecraft.noblelib.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import org.bukkit.command.CommandSender;

import java.util.function.Function;

public abstract class SubCommand extends BaseCommand {
    public SubCommand(Function<Command.Builder<CommandSender>, Command.Builder<CommandSender>> builder) {
        super(builder);
    }
    @Override
    public void register(CommandManager<CommandSender> mng, Command.Builder<CommandSender> builder) {
        mng.command(getCommand(builder));
    }
}
