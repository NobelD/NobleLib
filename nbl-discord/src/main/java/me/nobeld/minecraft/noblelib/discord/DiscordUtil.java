package me.nobeld.minecraft.noblelib.discord;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;

import java.util.Map;

public class DiscordUtil {
    public static MessageCreateData createEmbed(MessageEmbed embed) {
        return new MessageCreateBuilder().addEmbeds(embed).build();
    }
    private static String parse(String base, Map<String, String> map) {
        if (map == null || map.isEmpty()) return base;
        StrSubstitutor sub = new StrSubstitutor(map, "<", ">");
        String round = sub.replace(base);
        return sub.replace(round);
    }
    public static void sendMessage(TextChannel channel, String content, Emoji... reaction) {
        if (channel == null || content == null) return;
        channel.sendMessage(new MessageCreateBuilder().addContent(content).build()).queue(message -> {
            for (Emoji s : reaction) {
                message.addReaction(s).queue();
            }
        });
    }
    public static void sendMessage(TextChannel channel, MessageCreateData content, Emoji... reaction) {
        if (channel == null || content == null) return;
        channel.sendMessage(content).queue(message -> {
            for (Emoji s : reaction) {
                message.addReaction(s).queue();
            }
        });
    }
}
