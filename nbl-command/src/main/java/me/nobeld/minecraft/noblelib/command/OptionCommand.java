package me.nobeld.minecraft.noblelib.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.function.Function;

public abstract class OptionCommand extends BaseCommand {
    private final Function<Command.Builder<CommandSender>, Command.Builder<CommandSender>> base;
    private final List<BaseCommand> subCommand;
    public OptionCommand(Function<Command.Builder<CommandSender>, Command.Builder<CommandSender>> builder, Function<Command.Builder<CommandSender>, Command.Builder<CommandSender>> base, List<BaseCommand> subCommand) {
        super(builder);
        this.base = base;
        this.subCommand = subCommand;
    }
    @Override
    public void register(CommandManager<CommandSender> mng, Command.Builder<CommandSender> base) {
        Command.Builder<CommandSender> sub = getBaseBuilder(base);

        this.getSubCommand().forEach(cmd -> {
            if (cmd instanceof OptionCommand opt) {
                opt.register(mng, sub);
            } else {
                mng.command(cmd.getCommand(sub));
            }
        });
    }
    public Command.Builder<CommandSender> getBaseBuilder(Command.Builder<CommandSender> base) {
        return this.base.apply(base);
    }
    public List<BaseCommand> getSubCommand() {
        return subCommand;
    }
    @Override
    public Command.Builder<CommandSender> getCommand(Command.Builder<CommandSender> builder) {
        return null;
    }
}
