package me.bigvirusboi.potatowarp.util;

import me.bigvirusboi.potatowarp.PotatoWarp;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static int WARP_DELAY;

    public static boolean WARP_PARTICLES;
    public static boolean PREPARE_WARP_PARTICLES;

    public static void readConfig() {
        FileConfiguration config = PotatoWarp.getInstance().getConfig();
        WARP_DELAY = Math.max(config.getInt("warp-delay"), 0);
        WARP_PARTICLES = config.getBoolean("warp-particles");
        PREPARE_WARP_PARTICLES = config.getBoolean("prepare-warp-particles");
    }

    public static boolean shouldDelayWarp() {
        return WARP_DELAY != 0;
    }

    public static int warpDelayMillis() {
        return WARP_DELAY * 1000;
    }
}
