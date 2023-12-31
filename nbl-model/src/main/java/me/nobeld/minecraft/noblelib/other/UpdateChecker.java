package me.nobeld.minecraft.noblelib.other;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class UpdateChecker {
    public JavaPlugin plugin;
    public final String version;
    private final String url;
    private final String name;
    private long lastCheck = 0;
    private boolean cantReach = false;
    private String latest;
    @SuppressWarnings("deprecation")
    public UpdateChecker(JavaPlugin plugin, String url, String name) {
        this.plugin = plugin;
        this.name = name;
        this.url = url;
        version = plugin.getDescription().getVersion();
    }
    public UpdateStatus githubCheck() {
        if (System.currentTimeMillis() + 1800000 < lastCheck) return UpdateStatus.COOLDOWN;
        lastCheck = System.currentTimeMillis();
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

            int timed_out = 1500;
            con.setRequestProperty("accept", "application/vnd.github+json");
            con.setConnectTimeout(timed_out);
            con.setReadTimeout(timed_out);

            JsonElement element = JsonParser.parseReader(new InputStreamReader(con.getInputStream()));
            JsonObject json = element.getAsJsonObject();
            JsonArray array = json.get("assets").getAsJsonArray();
            for (JsonElement e : array) {
                String[] sub = e.getAsJsonObject().get("name").getAsString().split("-");
                if (sub.length < 2) return UpdateStatus.NO_DATA;
                if (sub[0].equalsIgnoreCase(name)) {
                    if (sub[1].endsWith(".jar")) sub[1] = sub[1].replace(".jar", "");
                    latest = sub[1];
                    break;
                }
            }
            if (latest == null) return UpdateStatus.NO_DATA;
            if (latest.contains(version)) return UpdateStatus.SAME_VERSION;
            return UpdateStatus.VERSION_AVAILABLE;
        } catch (Exception ex) {
            return UpdateStatus.CANT_REACH;
        }
    }
    public UpdateStatus simpleCheck() {
        if (System.currentTimeMillis() + 1800000 < lastCheck) return UpdateStatus.COOLDOWN;
        lastCheck = System.currentTimeMillis();
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

            int timed_out = 1500;
            con.setConnectTimeout(timed_out);
            con.setReadTimeout(timed_out);

            latest = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (latest == null) return UpdateStatus.NO_DATA;
            if (latest.contains(version)) return UpdateStatus.SAME_VERSION;
            return UpdateStatus.VERSION_AVAILABLE;
        } catch (Exception ex) {
            return UpdateStatus.CANT_REACH;
        }
    }
    public boolean canUpdate(boolean configUpdate, boolean isPlayer) {
        UpdateStatus status = githubCheck();
        if (!isPlayer && status == UpdateStatus.CANT_REACH) consoleError();
        if (!configUpdate) return false;
        return status == UpdateStatus.VERSION_AVAILABLE;
    }
    public void consoleError() {
        if (cantReach) return;
        cantReach = true;
        plugin.getLogger().log(Level.WARNING, "An error occurred while checking for updates.");
    }
    public String getLatest() {
        return latest;
    }
    public enum UpdateStatus {
        SAME_VERSION, VERSION_AVAILABLE, NO_DATA, CANT_REACH, COOLDOWN
    }
}
