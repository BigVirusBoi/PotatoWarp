package me.bigvirusboi.potatowarp;

import me.bigvirusboi.potatowarp.util.Messages;
import me.bigvirusboi.potatowarp.util.ReplaceString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Warp {
    private final String id;
    private Location location;
    private Material icon;

    public Warp(String id, Location location, Material icon) {
        this.id = id;
        this.location = location;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Material getIcon() {
        return icon;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public void warpPlayer(Player player) {
        if (!PotatoWarp.getInstance().isWarping(player)) {
            if (Config.shouldDelayWarp()) {
                Messages.sendMessage(player, Messages.WARP_DELAY, new ReplaceString("warp", id), new ReplaceString("time", Config.warpDelay()));
                PotatoWarp.getInstance().addWarpingPlayer(player, this);
            } else {
                forceWarp(player);
            }
        } else {
            Messages.sendMessage(player, Messages.CANCEL_WARP);
        }
    }

    public void forceWarp(Player player) {
        Messages.sendMessage(player, Messages.WARP, new ReplaceString("warp", id));
        player.teleport(location);
        Bukkit.getScheduler().runTaskLater(PotatoWarp.getInstance(), () -> player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, .5f, 1f), 1);
    }
}
