package me.bigvirusboi.potatowarp.warp;

import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.data.FileManager;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class WarpUtils {
    public static void readWarps() {
        Map<String, Warp> warps = new HashMap<>();

        YamlConfiguration yml = FileManager.getWarpsConfig();
        ConfigurationSection sec = yml.getConfigurationSection("Warps");
        if (sec != null) {
            for (String name : sec.getKeys(false)) {
                ConfigurationSection warpSec = sec.getConfigurationSection(name);
                if (warpSec != null) {
                    Warp warp = getWarp(warpSec);
                    if (warp != null) {
                        warps.put(name, warp);
                    }
                }
            }
        }

        PotatoWarp.getWarps().clear();
        PotatoWarp.getWarps().putAll(warps);
    }

    public static void createWarp(String id, Location loc, String mat) {
        Material icon = Material.getMaterial(mat.toUpperCase());
        if (icon != null) {
            createWarp(id, loc, icon);
        } else createWarp(id, loc, Material.CLAY_BALL);
    }

    public static void createWarp(String id, Location loc, Material icon) {
        YamlConfiguration yml = FileManager.getWarpsConfig();
        ConfigurationSection sec = getOrCreateSection(yml, "Warps." + id);
        sec.set("location", locToString(loc));
        sec.set("icon", icon.name());
        sec.set("glowing", false);
        sec.set("restricted", false);
        FileManager.saveFile(yml);
        PotatoWarp.getWarps().put(id, new Warp(id, loc, icon));
    }

    public static void saveWarp(Warp warp) {
        YamlConfiguration yml = FileManager.getWarpsConfig();
        ConfigurationSection sec = getOrCreateSection(yml, "Warps." + warp.getId());
        sec.set("location", locToString(warp.getLocation()));
        sec.set("icon", warp.getIcon().name());
        sec.set("glowing", warp.isGlowing());
        sec.set("restricted", warp.isRestricted());
        FileManager.saveFile(yml);
        PotatoWarp.getWarps().replace(warp.getId(), warp);
    }

    public static void deleteWarp(String id) {
        YamlConfiguration yml = FileManager.getWarpsConfig();
        ConfigurationSection sec = getOrCreateSection(yml, "Warps");
        sec.set(id, null);
        FileManager.saveFile(yml);
        PotatoWarp.getWarps().remove(id);
    }

    public static Warp getWarp(ConfigurationSection sec) {
        String id = sec.getName();
        String location = sec.getString("location");
        if (location != null) {
            Location loc = locFromString(location);
            if (loc != null) {
                String iconName = sec.getString("icon");
                if (iconName != null) {
                    Material icon = Material.getMaterial(iconName);
                    if (icon != null) {
                        boolean glowing = sec.getBoolean("glowing");
                        boolean restricted = sec.getBoolean("restricted");
                        return new Warp(id, loc, icon, glowing, restricted);
                    }
                }
                return new Warp(id, loc, Material.CLAY_BALL);
            }
        }
        return null;
    }

    public static Warp getWarp(String id) {
        return PotatoWarp.getWarps().get(id);
    }

    public static Location locFromString(String name) {
        String[] string = name.split(",");
        if (string.length >= 4) {
            World world = Bukkit.getWorld(string[0]);
            if (world != null) {
                double x = Double.parseDouble(string[1]);
                double y = Double.parseDouble(string[2]);
                double z = Double.parseDouble(string[3]);
                if (string.length >= 6) {
                    float yaw = Float.parseFloat(string[4]);
                    float pitch = Float.parseFloat(string[5]);
                    return new Location(world, x, y, z, yaw, pitch);
                }
                return new Location(world, x, y, z);
            }
        }
        return null;
    }

    public static String locToString(Location loc) {
        World world = loc.getWorld();
        if (world != null) {
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            float yaw = loc.getYaw();
            float pitch = loc.getPitch();
            return String.format("%s,%s,%s,%s,%s,%s", world.getName(), x, y, z, yaw, pitch);
        } else return null;
    }

    private static ConfigurationSection getOrCreateSection(YamlConfiguration yml, String path) {
        ConfigurationSection sec = yml.getConfigurationSection(path);
        if (sec != null) {
            return sec;
        } else return yml.createSection(path);
    }

    public static ItemStack createWarpItem(Warp warp, boolean hasLore, boolean hasId) {
        List<String> lore = new ArrayList<>();

        ItemStack stack = new ItemStack(warp.getIcon(), 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName("§b§n" + warp.getId());
        if (warp.isGlowing()) {
            meta.addEnchant(Enchantment.THORNS, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (warp.isRestricted()) lore.add("§c§lRESTRICTED");
        if (hasLore) lore.add("§eClick to warp!");
        if (hasId) meta.getPersistentDataContainer().set(new NamespacedKey(PotatoWarp.getInstance(), "id"), PersistentDataType.STRING, warp.getId());
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return stack;
    }
}
