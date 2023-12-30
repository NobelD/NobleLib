package me.nobeld.minecraft.noblelib.time;

public class TimeExtra {
    public static long dayToTick(int time) {
        return (long) time * 60 * 60 * 24 * 20;
    }
    public static long tickToDay(int time) {
        return (long) time / (60 * 60 * 24 * 20);
    }
    public static long hourToTick(int time) {
        return (long) time * 60 * 60 * 20;
    }
    public static long minToTick(int time) {
        return (long) time * 60 * 20;
    }
    public static long secToTick(int time) {
        return (long) time * 20;
    }
    public static long timestampToTick(long timestamp) {
        return timestamp / 50;
    }
    public static long tickToTimestamp(long tick) {
        return tick * 50;
    }
}
