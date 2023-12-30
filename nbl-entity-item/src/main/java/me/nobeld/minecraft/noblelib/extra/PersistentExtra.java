package me.nobeld.minecraft.noblelib.extra;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PersistentExtra {
    /** Get a stored value from an item by a type.
     * @param item the item to retrieve the data
     * @param key the key of the stored data
     * @param type the type of the stored data
     * @param def the returned value if none is found
     * @return the stored value or def if none is found
     */
    public static <T> T getPersistent(@Nullable ItemStack item, @NotNull NamespacedKey key, @NotNull PersistentDataType<?, T> type, @NotNull T def) {
        if (item == null) return def;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return def;
        return getPersistent(meta, key, type, def);
    }
    /** Get a stored value from a data holder by a type.
     * @param holder the holder to retrieve the data
     * @param key the key of the stored data
     * @param type the type of the stored data
     * @param def the returned value if none is found
     * @return the stored value or def if none is found
     */
    public static <T> T getPersistent(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key, @NotNull PersistentDataType<?, T> type, @Nullable T def) {
        if (holder == null) return def;
        if (!holder.getPersistentDataContainer().has(key)) return def;
        T data = holder.getPersistentDataContainer().get(key, type);
        if (data == null) return def;
        return data;
    }
    /** Set a stored value to an item by a type.
     * @param item the item to set the data
     * @param key the key of the data
     * @param type the type of the data
     * @param value the data to set
     */
    public static <T> void setPersistent(@Nullable ItemStack item, @NotNull NamespacedKey key, @NotNull PersistentDataType<?, T> type, @NotNull T value) {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().set(key, type, value);
    }
    /** Set a stored value to a data holder by a type.
     * @param holder the data holder to set the data
     * @param key the key of the data
     * @param type the type of the data
     * @param value the data to set
     */
    public static <T> void setPersistent(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key, @NotNull PersistentDataType<?, T> type, @NotNull T value) {
        if (holder == null) return;
        holder.getPersistentDataContainer().set(key, type, value);
    }
    /** Set a stored boolean to a data holder.
     * @param holder the data holder to set the data
     * @param key the key of the data
     * @param value the data to set
     */
    public static void setPersistentB(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key, boolean value) {
        setPersistent(holder, key, PersistentDataType.BOOLEAN, value);
    }
    /** get a stored boolean from a data holder.
     * @param holder the data holder to get the data
     * @param key the key of the data
     * @return the data or false if none found
     */
    public static boolean getPersistentB(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key) {
        return getPersistent(holder, key, PersistentDataType.BOOLEAN, false);
    }
    /** Set a stored integer to a data holder.
     * @param holder the data holder to set the data
     * @param key the key of the data
     * @param value the data to set
     */
    public static void setPersistentI(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key, int value) {
        setPersistent(holder, key, PersistentDataType.INTEGER, value);
    }
    /** get a stored integer from a data holder.
     * @param holder the data holder to get the data
     * @param key the key of the data
     * @return the data or -1 if none found
     */
    public static int getPersistentI(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key) {
        return getPersistent(holder, key, PersistentDataType.INTEGER, -1);
    }
    /** Set a stored double to a data holder.
     * @param holder the data holder to set the data
     * @param key the key of the data
     * @param value the data to set
     */
    public static void setPersistentD(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key, double value) {
        setPersistent(holder, key, PersistentDataType.DOUBLE, value);
    }
    /** get a stored double from a data holder.
     * @param holder the data holder to get the data
     * @param key the key of the data
     * @return the data or -1.0 if none found
     */
    public static double getPersistentD(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key) {
        return getPersistent(holder, key, PersistentDataType.DOUBLE, -1.0D);
    }
    /** Set a stored string to a data holder.
     * @param holder the data holder to set the data
     * @param key the key of the data
     * @param value the data to set
     */
    public static void setPersistentS(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key, @NotNull String value) {
        setPersistent(holder, key, PersistentDataType.STRING, value);
    }
    /** get a stored string from a data holder.
     * @param holder the data holder to get the data
     * @param key the key of the data
     * @return the data or null if none found
     */
    @Nullable
    public static String getPersistentS(@Nullable PersistentDataHolder holder, @NotNull NamespacedKey key) {
        return getPersistent(holder, key, PersistentDataType.STRING, null);
    }
}
