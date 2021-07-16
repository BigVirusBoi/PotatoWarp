package me.bigvirusboi.potatowarp;

public class Config {
    public static boolean shouldDelayWarp() {
        return warpDelay() != 0;
    }

    public static int warpDelay() {
        return Math.max(PotatoWarp.getInstance().getConfig().getInt("warp-delay"), 0);
    }

    public static int warpDelayMillis() {
        return warpDelay() * 1000;
    }
}
