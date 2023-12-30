package me.nobeld.minecraft.noblelib.extra;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.security.SecureRandom;

public class PlayerExtra {
    public static Location randomLocation(Location loc, int minX, int maxX, int minZ, int maxZ) {
        return randomLocation(loc, minX, maxX, loc.getWorld().getMinHeight(), loc.getWorld().getMaxHeight(), minZ, maxZ);
    }
    public static Location randomLocation(Location loc, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        SecureRandom random = new SecureRandom();

        if (minX > maxZ) minX = maxX;
        if (minY > maxZ) minY = maxY;
        if (minZ > maxZ) minZ = maxZ;

        int rx = minX == maxX ? maxX : random.nextInt(minX, maxX) * (random.nextBoolean() ? 1 : -1);
        int rz = minZ == maxZ ? maxZ : random.nextInt(minZ, maxZ) * (random.nextBoolean() ? 1 : -1);

        int y = loc.getWorld().getHighestBlockAt(rx, rz).getY();
        if (y < minY || y > maxY) y = loc.getBlockY();

        return loc.add(rx, y, rz);
    }
    public static boolean hasTotem(Player player) {
        return itemAmount(player, Material.TOTEM_OF_UNDYING) > 0;
    }
    public static int totemAmount(Player player) {
        return itemAmount(player, Material.TOTEM_OF_UNDYING);
    }
    public static int itemAmount(Player player, Material type) {
        int count = 0;
        PlayerInventory inventory = player.getInventory();
        for (ItemStack stack : inventory.all(type).values()) {
            if (stack == null || stack.getType() != type) continue;
            count += stack.getAmount();
        }
        ItemStack stack = player.getEquipment().getItemInOffHand();
        if (stack.getType() == type) count += stack.getAmount();
        return count;
    }
    public static int itemAmountSingular(Player player, Material type) {
        int count = 0;
        PlayerInventory inventory = player.getInventory();
        for (ItemStack stack : inventory.all(type).values()) {
            if (stack == null || stack.getType() != type) continue;
            count++;
        }
        ItemStack stack = player.getEquipment().getItemInOffHand();
        if (stack.getType() == type) count++;
        return count;
    }
    public static BlockFace yawToFace(float yaw) {
        BlockFace[] subFace = {
                BlockFace.NORTH,
                BlockFace.NORTH_EAST,
                BlockFace.EAST,
                BlockFace.SOUTH_EAST,
                BlockFace.SOUTH,
                BlockFace.SOUTH_WEST,
                BlockFace.WEST,
                BlockFace.NORTH_WEST
        };

        return subFace[Math.round(yaw / 45f) & 0x7];
    }
}
