package me.nobeld.minecraft.noblelib.time;

public class TimeContainer {
    private int day;
    private int hour;
    private int min;
    private int sec;
    private long totalTicks;
    private long timestamp;
    @SuppressWarnings("CopyConstructorMissesField")
    public TimeContainer(TimeContainer container) {
        this(container.getTotalTick());
    }
    public TimeContainer(int day, int hour, int min, int sec) {
        this(TimeExtra.dayToTick(day) + TimeExtra.hourToTick(hour) + TimeExtra.minToTick(min) + TimeExtra.secToTick(sec));
    }
    public TimeContainer() {
        this(0);
    }
    public TimeContainer(long totalTicks) {
        timestamp = 0;
        this.totalTicks = totalTicks;
        refresh();
    }
    private void refresh() {
        if (totalTicks < 0) totalTicks = 0;
        int raw = (int) totalTicks / 20;

        this.day = raw / 86400;
        this.hour = (raw / 3600) % 24;
        this.min = (raw / 60) % 60;
        this.sec = raw % 60;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public void setTimestamp() {
        this.timestamp = System.currentTimeMillis();
    }
    public void tick() {
        this.totalTicks -= 1;
        refresh();
    }
    public void tickSecond() {
        this.totalTicks -= 20;
        refresh();
    }
    public void tickValue(int value) {
        this.totalTicks -= value;
        refresh();
    }
    public void tickTimestamp(long timestamp) {
        long rest = timestamp - this.timestamp;
        setTimestamp(timestamp);
        reduceTotalTick(TimeExtra.timestampToTick(rest));
    }
    public void tickTimestamp() {
        tickTimestamp(System.currentTimeMillis());
    }
    public long getTotalTick() {
        return totalTicks;
    }
    public void setTotalTick(long ticks) {
        totalTicks = ticks;
        refresh();
    }
    public void setTotalTick(TimeContainer time) {
        totalTicks = time.getTotalTick();
        refresh();
    }
    public void addTotalTick(long ticks) {
        totalTicks += ticks;
        refresh();
    }
    public void reduceTotalTick(long ticks) {
        totalTicks -= ticks;
        refresh();
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        totalTicks = TimeExtra.dayToTick(day);
        refresh();
    }
    public void addDay(int day) {
        totalTicks += TimeExtra.dayToTick(day);
        refresh();
    }
    public void reduceDay(int day) {
        totalTicks -= TimeExtra.dayToTick(day);
        refresh();
    }
    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        totalTicks = TimeExtra.hourToTick(hour);
        refresh();
    }
    public void addHour(int hour) {
        totalTicks += TimeExtra.hourToTick(hour);
        refresh();
    }
    public void reduceHour(int hour) {
        totalTicks -= TimeExtra.hourToTick(hour);
        refresh();
    }
    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        totalTicks = TimeExtra.minToTick(min);
        refresh();
    }
    public void addMin(int min) {
        totalTicks += TimeExtra.minToTick(min);
        refresh();
    }
    public void reduceMin(int min) {
        totalTicks -= TimeExtra.minToTick(min);
        refresh();
    }
    public int getSec() {
        return sec;
    }
    public void setSec(int sec) {
        totalTicks = TimeExtra.secToTick(sec);
        refresh();
    }
    public void addSec(int sec) {
        totalTicks += TimeExtra.secToTick(sec);
        refresh();
    }
    public void reduceSec(int sec) {
        totalTicks -= TimeExtra.secToTick(sec);
        refresh();
    }
    public TimeContainer copy() {
        return new TimeContainer(this);
    }
}
