package me.bigvirusboi.potatowarp;

import me.bigvirusboi.potatowarp.commands.WarpCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class PotatoWarp extends JavaPlugin {
    public static final Map<String, Warp> WARPS = new HashMap<>();

    public PotatoWarp instance;

    @Override
    public void onEnable() {
        instance = this;

        //saveDefaultConfig();
        WARPS.putAll(WarpUtils.readWarps());

        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("warp").setTabCompleter(new WarpCommand());

        FileManager.setup();
    }

    @Override
    public void onDisable() {
        WARPS.clear();
        instance = null;
    }

    public PotatoWarp getInstance() {
        return instance;
    }
}
