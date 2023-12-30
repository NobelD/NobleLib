package me.nobeld.minecraft.noblelib.adventure;

import me.clip.placeholderapi.PlaceholderAPI;
import me.nobeld.minecraft.noblelib.AdventureLib;
import me.nobeld.minecraft.noblelib.time.TimeContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.nobeld.minecraft.noblelib.AdventureLib.*;

public class MiniResolver {
    private final AdventureLib lib;
    public MiniResolver(AdventureLib lib) {
        this.lib = lib;
    }
    public static String getLegacy(Component component) {
        LegacyComponentSerializer legacySerializer = LegacyComponentSerializer.builder()
                .character('§')
                .hexColors()
                .extractUrls()
                .useUnusualXRepeatedCharacterHexFormat()
                .build();
        return legacySerializer.serialize(component);
    }
    public static Component formatMini(String text) {
        MiniMessage minimessage = MiniMessage.builder().tags(TagResolver.builder()
                        .resolver(StandardTags.defaults())
                        .resolver(hasPlaceholderAPI  ? papiTag(null) : noPapiTag())
                        .build())
                .build();
        return minimessage.deserialize(text);
    }
    public static Component formatMini(String text, Player player) {
        MiniMessage minimessage = MiniMessage.builder().tags(TagResolver.builder()
                        .resolver(StandardTags.defaults())
                        .resolver(hasPlaceholderAPI  ? papiTag(player) : noPapiTag())
                        .build())
                .build();
        return minimessage.deserialize(text);
    }
    private TagResolver.Builder baseBuilder(Player player) {
        return TagResolver.builder()
                .resolver(StandardTags.defaults())
                .resolver(prefix())
                .resolver(hasPlaceholderAPI  ? papiTag(player) : noPapiTag())
                .resolver(player != null  ? playerName(player) : noPlayerName());
    }
    public Component formatAll(String text, Player player) {
        MiniMessage minimessage = MiniMessage.builder().tags(baseBuilder(player).build()).build();
        return minimessage.deserialize(text);
    }
    public Component formatAll(String text, String name) {
        MiniMessage minimessage = MiniMessage.builder().tags(TagResolver.builder()
                        .resolver(StandardTags.defaults())
                        .resolver(prefix())
                        .resolver(hasPlaceholderAPI  ? papiTag(null) : noPapiTag())
                        .resolver(playerName(name))
                        .build())
                .build();
        return minimessage.deserialize(text);
    }
    public Component formatAll(String text) {
        return formatAll(text, (Player) null);
    }
    public Component formatExtra(String text, Player player, Component extra) {
        MiniMessage minimessage = MiniMessage.builder().tags(baseBuilder(player).build()).build();
        return minimessage.deserialize(text, Placeholder.component("extra", extra));
    }
    public Component formatLoc(String text, Player player, String world, int x, int y, int z) {
        MiniMessage minimessage = MiniMessage.builder().tags(baseBuilder(player).resolver(locTag(world, x, y, z)).build()).build();
        return minimessage.deserialize(text);
    }
    public Component formatLoc(String text, Player player, Location location) {
        return formatLoc(text, player, location.getWorld().getName(), (int) location.getX(), (int) location.getY(), (int) location.getZ());
    }
    public Component formatCommand(CommandSender sender, String text) {
        Player player1 = null;
        if (sender instanceof Player player) player1 = player;
        return formatAll(text, player1);
    }
    public Component formatColor(String text, Player player) {
        MiniMessage minimessage = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.color())
                        .resolver(StandardTags.decorations())
                        .resolver(StandardTags.reset())
                        .resolver(StandardTags.gradient())
                        .resolver(prefix())
                        .resolver(hasPlaceholderAPI ? papiTag(player) : noPapiTag())
                        .resolver(player != null  ? playerName(player) : noPlayerName())
                        .build()
                )
                .build();
        return minimessage.deserialize(text);
    }
    public Component formatTime(String text, Player player, TimeContainer time) {
        return formatTime(text, player, time.getDay(), time.getHour(), time.getMin(), time.getSec());
    }
    public Component formatTime(String text, Player player, int d, int h, int m, int s) {
        MiniMessage minimessage = MiniMessage.builder().tags(baseBuilder(player)
                        .resolver(counterTag(d, h, m, s))
                        .resolver(counter2Tag(d, h, m, s))
                        .resolver(timeSingleTag(d, h, m, s))
                        .resolver(timeMultiTag(d, h, m, s))
                        .resolver(timeDefTag(d, h, m, s))
                        .build()
                )
                .build();
        return minimessage.deserialize(text);
    }
    public Component formatSleep(String text, Player player, String name, int rest, int need) {
        MiniMessage minimessage = MiniMessage.builder().tags(baseBuilder(player).resolver(sleepTag(name, rest, need)).build()).build();
        return minimessage.deserialize(text);
    }
    public Component formatLive(String text, Player player, String name, int used, int total) {
        MiniMessage minimessage = MiniMessage.builder().tags(baseBuilder(player).resolver(liveTag(name, used, total)).build()).build();
        return minimessage.deserialize(text);
    }
    public Component formatDamage(String text, String name, String damage) {
        MiniMessage minimessage = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.defaults())
                        .resolver(prefix())
                        .resolver(damageAmount(damage))
                        .resolver(hasPlaceholderAPI ? papiTag(null) : noPapiTag())
                        .resolver(playerName(name))
                        .build()
                )
                .build();
        return minimessage.deserialize(text);
    }
    public Component formatTotem(String text, Player player, String name, int amount, int probMin, int probMax) {
        MiniMessage minimessage = MiniMessage.builder()
                .tags(TagResolver.builder()
                        .resolver(StandardTags.defaults())
                        .resolver(prefix())
                        .resolver(hasPlaceholderAPI  ? papiTag(player) : noPapiTag())
                        .resolver(playerName(name))
                        .resolver(amountUsed(amount))
                        .resolver(probTotem(probMin, probMax))
                        .build()
                )
                .build();
        return minimessage.deserialize(text);
    }
    private @NotNull TagResolver prefix() {
        MiniMessage miniMessage = MiniMessage.builder().tags(TagResolver.standard()).build();
        final Component component = miniMessage.deserialize(lib.usePrefix() ? lib.getPrefix() + " " : "");
        return TagResolver.resolver("prefix", Tag.selfClosingInserting(component));
    }
    private static @NotNull TagResolver playerName(final @NotNull Player player) {
        final Component component = Component.text(player.getName());
        return TagResolver.resolver("player", Tag.selfClosingInserting(component));
    }
    private static @NotNull TagResolver playerName(String name) {
        final Component component = Component.text(name);
        return TagResolver.resolver("player", Tag.selfClosingInserting(component));
    }
    private static @NotNull TagResolver noPlayerName() {
        final Component component = Component.text("name");
        return TagResolver.resolver("player", Tag.selfClosingInserting(component));
    }
    private static @NotNull TagResolver damageAmount(String damage) {
        final Component component = Component.text(damage);
        return TagResolver.resolver("damage", Tag.selfClosingInserting(component));
    }
    private static @NotNull TagResolver probTotem(int min, int max) {
        return TagResolver.resolver("prob", (argumentQueue, context) -> {
            final String arg = argumentQueue.popOr("is necessary an argument").value();

            String parsed = "";
            switch (arg.toLowerCase()) {
                case "min" -> parsed = String.valueOf(min);
                case "max" -> parsed = String.valueOf(max);
                case "sign" -> parsed = min < max ? "!=" : min == max ? "==" : ">=";
            }

            final Component component = Component.text(parsed);
            return Tag.selfClosingInserting(component);
        });
    }
    private static @NotNull TagResolver amountUsed(int amount) {
        final Component component = Component.text(amount);
        return TagResolver.resolver("amount", Tag.selfClosingInserting(component));
    }
    private static @NotNull TagResolver sleepTag(String name, int rest, int need) {
        return TagResolver.resolver("sleep", (argumentQueue, context) -> {
            final String arg = argumentQueue.popOr("is necessary an argument").value();
            String parsed = "";

            switch (arg.toLowerCase()) {
                case "player" -> parsed = name;
                case "rest" -> parsed = String.valueOf(rest);
                case "need" -> parsed = String.valueOf(need);
            }

            final Component component = Component.text(parsed);
            return Tag.selfClosingInserting(component);
        });
    }
    private static @NotNull TagResolver liveTag(String name, int used, int total) {
        return TagResolver.resolver("lives", (argumentQueue, context) -> {
            final String arg = argumentQueue.popOr("is necessary an argument").value();
            String parsed = "";

            switch (arg.toLowerCase()) {
                case "player" -> parsed = name;
                case "used" -> parsed = String.valueOf(used);
                case "left" -> parsed = String.valueOf(total - used);
                case "total" -> parsed = String.valueOf(total);
            }

            final Component component = Component.text(parsed);
            return Tag.selfClosingInserting(component);
        });
    }
    private static @NotNull TagResolver counter2Tag(int d, int h, int m, int s) {
        return TagResolver.resolver("count2", (argumentQueue, context) -> {
            final String arg = argumentQueue.popOr("is necessary an argument").value();

            String day = String.format("%02d", d);
            String hour = String.format("%02d", h);
            String min = String.format("%02d", m);
            String sec = String.format("%02d", s);
            String parsed = "";

            switch (arg.toLowerCase()) {
                case "day" -> parsed = day + ":" + hour + ":" + min + ":" + sec;
                case "hour" -> parsed = hour + ":" + min + ":" + sec;
                case "min" -> parsed = min + ":" + sec;
                case "sec" -> parsed = sec;
            }

            final Component component = Component.text(parsed);
            return Tag.selfClosingInserting(component);
        });
    }
    private static @NotNull TagResolver counterTag(int d, int h, int m, int s) {
        String day = d + " day(s) ";
        String hour = String.format("%02d", h);
        String min = String.format("%02d", m);
        String sec = String.format("%02d", s);
        String parsed = (d > 0 ? day : "") + hour + ":" + min + ":" + sec;

        final Component component = Component.text(parsed);
        return TagResolver.resolver("count", Tag.selfClosingInserting(component));
    }
    private static @NotNull TagResolver timeSingleTag(int d, int h, int m, int s) {
        return TagResolver.resolver("time2", (argumentQueue, context) -> {
            final String arg = argumentQueue.popOr("is necessary an argument").value();

            String parsed = "";
            switch (arg) {
                case "day" -> parsed = d + " day(s)";
                case "hour" -> parsed = h + " hour(s)";
                case "min" -> parsed = m + " min(s)";
                case "sec" -> parsed = s + " sec(s)";
            }

            final Component component = Component.text(parsed);
            return Tag.selfClosingInserting(component);
        });
    }
    private static @NotNull TagResolver timeMultiTag(int d, int h, int m, int s) {
        return TagResolver.resolver("time3", (argumentQueue, context) -> {
            final String arg = argumentQueue.popOr("is necessary an argument").value();

            String day = d + " day(s) ";
            String hour = h + " hour(s) ";
            String min = m + " min(s) ";
            String sec = s + " sec(s)";
            String parsed = "";

            switch (arg.toLowerCase()) {
                case "day" -> parsed = day + hour + min + sec;
                case "hour" -> parsed = hour + min + sec;
                case "min" -> parsed = min + sec;
                case "sec" -> parsed = sec;
            }


            final Component component = Component.text(parsed);
            return Tag.selfClosingInserting(component);
        });
    }
    private static @NotNull TagResolver timeDefTag(int d, int h, int m, int s) {
        String day = d > 0 ? "&" + d + " day(s)&" : "";
        String hour = h > 0 ? "&" + h + " hour(s)&" : "";
        String min = m > 0 ? "&" + m + " min(s)&" : "";
        String sec = s > 0 ? "&" + s + " sec(s)&" : "";
        String parsed = day + hour + min + sec;

        parsed = parsed.replace("&&", " ");
        parsed = parsed.replace("&", "");

        final Component component = Component.text(parsed);
        return TagResolver.resolver("time",Tag.selfClosingInserting(component));
    }
    private static @NotNull TagResolver locTag(String world, int x, int y, int z) {
        return TagResolver.resolver("loc", (argumentQueue, context) -> {
            final String arg = argumentQueue.popOr("is necessary an argument").value();

            String parsed = "";
            switch (arg) {
                case "world" -> parsed = world;
                case "x" -> parsed = String.valueOf(x);
                case "y" -> parsed = String.valueOf(y);
                case "z" -> parsed = String.valueOf(z);
            }

            final Component component = Component.text(parsed);
            return Tag.selfClosingInserting(component);
        });
    }
    private static @NotNull TagResolver papiTag(final Player player) {
        return TagResolver.resolver("papi", (argumentQueue, context) -> {
            final String papiPlaceholder = argumentQueue.popOr("papi tag requires an argument").value();
            final String parsedPlaceholder = PlaceholderAPI.setPlaceholders(player, '%' + papiPlaceholder + '%');

            final Component componentPlaceholder = LegacyComponentSerializer.legacySection().deserialize(parsedPlaceholder);
            return Tag.selfClosingInserting(componentPlaceholder);
        });
    }
    private static @NotNull TagResolver noPapiTag() {
        return TagResolver.resolver("papi", (a, c) -> {
            final String arg = a.popOr("is necessary an argument").value();
            final Component component = Component.text(arg);
            return Tag.selfClosingInserting(component);
        });
    }
}
