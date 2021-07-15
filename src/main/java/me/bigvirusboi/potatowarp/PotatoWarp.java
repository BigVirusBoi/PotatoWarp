package me.bigvirusboi.potatowarp;

import me.bigvirusboi.potatowarp.commands.*;
import me.bigvirusboi.potatowarp.menu.system.MenuListener;
import me.bigvirusboi.potatowarp.menu.system.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class PotatoWarp extends JavaPlugin {
    public static final Map<String, Warp> WARPS = new HashMap<>();
    private static final HashMap<Player, PlayerMenuUtility> pmuMap = new HashMap<>();

    public static PotatoWarp instance;

    @Override
    public void onEnable() {
        instance = this;

        //saveDefaultConfig();
        FileManager.setup();
        WarpUtils.readWarps();

        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("warp").setTabCompleter(new WarpCommand());
        getCommand("warps").setExecutor(new WarpsCommand());
        getCommand("setwarp").setExecutor(new SetwarpCommand());

        Bukkit.getScheduler().runTaskTimer(this, WarpUtils::readWarps, 10, 100);
    }

    @Override
    public void onDisable() {
        WARPS.clear();
        instance = null;
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
