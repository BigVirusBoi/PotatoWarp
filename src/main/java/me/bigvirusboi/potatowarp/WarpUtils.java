package me.bigvirusboi.potatowarp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.Map;

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

    public static void createWarp(String id, Location loc) {
        YamlConfiguration yml = FileManager.getWarpsConfig();
        ConfigurationSection sec = getOrCreateSection(yml, "Warps");
        ConfigurationSection section = sec.createSection(id);
        section.set("location", locToString(loc));
        FileManager.saveFile(yml);
        PotatoWarp.getWarps().put(id, new Warp(id, loc));
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
                return new Warp(id, loc);
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
}
