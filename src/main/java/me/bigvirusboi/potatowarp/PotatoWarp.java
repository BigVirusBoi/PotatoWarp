package me.bigvirusboi.potatowarp;

import me.bigvirusboi.potatowarp.commands.*;
import me.bigvirusboi.potatowarp.menu.system.MenuListener;
import me.bigvirusboi.potatowarp.menu.system.PlayerMenuUtility;
import me.bigvirusboi.potatowarp.util.FileManager;
import me.bigvirusboi.potatowarp.util.Messages;
import me.bigvirusboi.potatowarp.util.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.TreeMap;

public final class PotatoWarp extends JavaPlugin {
    public static final HashMap<Player, Long> PLAYER_TIME = new HashMap<>();
    public static final HashMap<Player, Warp> PLAYER_WARP = new HashMap<>();
    private static final TreeMap<String, Warp> WARPS = new TreeMap<>();
    private static final HashMap<Player, PlayerMenuUtility> pmuMap = new HashMap<>();

    private static PotatoWarp instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        FileManager.setup();
        WarpUtils.readWarps();

        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("warp").setTabCompleter(new WarpCommand());
        getCommand("warps").setExecutor(new WarpsCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("delwarp").setExecutor(new DelWarpCommand());
        getCommand("delwarp").setTabCompleter(new DelWarpCommand());

        Bukkit.getScheduler().runTaskTimer(this, WarpUtils::readWarps, 10, 100);
        Bukkit.getScheduler().runTaskTimer(this, this::warpPlayers, 0, 1);
    }

    public void warpPlayers() {
        for (Player player : PLAYER_TIME.keySet()) {
            if (PLAYER_TIME.get(player) <= System.currentTimeMillis()) {
                PLAYER_WARP.get(player).forceWarp(player);
                PLAYER_TIME.remove(player);
                PLAYER_WARP.remove(player);
            }
        }
    }

    public void cancelWarp(Player player) {
        if (PLAYER_TIME.containsKey(player)) {
            PLAYER_TIME.remove(player);
            PLAYER_WARP.remove(player);
            Messages.sendMessage(player, Messages.CANCEL_WARP);
        }
    }

    public void addWarpingPlayer(Player player, Warp warp) {
        PLAYER_TIME.put(player, System.currentTimeMillis() + Config.warpDelayMillis());
        PLAYER_WARP.put(player, warp);
    }

    public boolean isWarping(Player player) {
        return PLAYER_TIME.containsKey(player);
    }

    @Override
    public void onDisable() {
        WARPS.clear();
        PLAYER_TIME.clear();
        PLAYER_WARP.clear();
        instance = null;
    }

    public static TreeMap<String, Warp> getWarps() {
        return WARPS;
    }

    public static PlayerMenuUtility getPMU(Player p) {
        PlayerMenuUtility pmu;
        if (!(pmuMap.containsKey(p))) {
            pmu = new PlayerMenuUtility(p);
            pmuMap.put(p, pmu);

            return pmu;
        } else {
            return pmuMap.get(p);
        }
    }

    public static PotatoWarp getInstance() {
        return instance;
    }
}
