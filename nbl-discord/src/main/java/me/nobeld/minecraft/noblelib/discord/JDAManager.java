package me.nobeld.minecraft.noblelib.discord;

import me.nobeld.minecraft.noblelib.discord.model.InteractResult;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.*;

public abstract class JDAManager {
    public JDA bot;
    public ListenerAdapter listener;
    public final Set<String> channels = new HashSet<>();
    public final List<CommandData> commandsList = new ArrayList<>();
    public JDAManager(JDA bot, ListenerAdapter cmdListener) {
        this.bot = bot;
        this.listener = cmdListener;
    }
    public void loadBot() {
        loadChannels();
        onStart();
        bot.addEventListener(listener);
        addCommands(commandsList);
    }
    public List<CommandData> getCommandsList() {
        return commandsList;
    }
    public void addCommands(List<CommandData> list) {
        bot.updateCommands().addCommands(list).queue();
    }
    abstract void loadChannels();
    abstract void onStart();
    abstract void onDisable();
    public boolean notChannel(InteractResult event, List<String> list) {
        if (list == null || list.isEmpty()) return true;
        List<TextChannel> permittedChannels = new ArrayList<>();

        for (String c : list) {
            TextChannel ch = getChannel(c);
            if (ch != null) permittedChannels.add(ch);
        }
        MessageChannelUnion c = event.getBaseEvent().getChannel();
        if (c.getType() != ChannelType.TEXT) return false;
        return !permittedChannels.contains(c.asTextChannel());
    }
    public boolean notRole(Member member, List<String> list, boolean everyone) {
        if (list == null || list.isEmpty()) return true;
        if (everyone) {
            return false;
        }

        List<Role> permittedRoles = new ArrayList<>();

        for (Map.Entry<String, List<Role>> entry : getConfigRoles().entrySet()) {
            for (String r : list) {
                if (entry.getKey().equalsIgnoreCase(r)) permittedRoles.addAll(entry.getValue());
            }
        }
        return Collections.disjoint(member.getRoles(), permittedRoles);
    }
    abstract Map<String, List<Role>> getConfigRoles();
    private List<Role> getRoleType(List<String> list) {
        if ((list) == null) return Collections.emptyList();
        return (list).stream().map(r -> {
            long id;
            try {
                id = Long.parseLong(r.trim());
            } catch (NumberFormatException ignored) {
                return null;
            }
            return bot.getRoleById(id);
        }).filter(Objects::nonNull).toList();
    }
    abstract TextChannel getChannel(String channel);
}
