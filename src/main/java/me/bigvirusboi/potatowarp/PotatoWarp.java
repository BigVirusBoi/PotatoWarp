package me.bigvirusboi.potatowarp;

import me.bigvirusboi.potatowarp.commands.*;
import me.bigvirusboi.potatowarp.menu.system.MenuListener;
import me.bigvirusboi.potatowarp.data.FileManager;
import me.bigvirusboi.potatowarp.data.Messages;
import me.bigvirusboi.potatowarp.warp.Warp;
import me.bigvirusboi.potatowarp.warp.WarpUtils;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class PotatoWarp extends JavaPlugin {
    private static final HashMap<String, Warp> WARPS = new HashMap<>();
    public static final HashMap<Player, Long> PLAYER_TIME = new HashMap<>();
    public static final HashMap<Player, Warp> PLAYER_WARP = new HashMap<>();

    private static PotatoWarp instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        Config.readConfig();

        FileManager.setup();
        WarpUtils.readWarps();

        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("warp").setTabCompleter(new WarpCommand());
        getCommand("warps").setExecutor(new WarpsCommand());
        getCommand("setwarp").setExecutor(new SetWarpCommand());
        getCommand("setwarp").setTabCompleter(new SetWarpCommand());
        getCommand("delwarp").setExecutor(new DelWarpCommand());
        getCommand("delwarp").setTabCompleter(new DelWarpCommand());
        getCommand("movewarp").setExecutor(new MoveWarpCommand());
        getCommand("movewarp").setTabCompleter(new MoveWarpCommand());

        Bukkit.getScheduler().runTaskTimer(this, WarpUtils::readWarps, 10, 40);
        Bukkit.getScheduler().runTaskTimer(this, this::warpPlayers, 0, 1);
    }

    public void warpPlayers() {
        for (Player player : PLAYER_TIME.keySet()) {
            if (Config.PREPARE_WARP_PARTICLES) {
                player.getWorld().spawnParticle(Particle.PORTAL, player.getLocation(), 25, 0, 0, 0, 3);
            }
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

    public static HashMap<String, Warp> getWarps() {
        return WARPS;
    }

    public static PotatoWarp getInstance() {
        return instance;
    }
}
