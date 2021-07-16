package me.bigvirusboi.potatowarp.warp;

import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.data.Config;
import me.bigvirusboi.potatowarp.data.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Warp {
    private final String id;
    private Location location;
    private final Material icon;
    private boolean glowing;
    private boolean restricted;

    public Warp(String id, Location location, Material icon) {
        this.id = id;
        this.location = location;
        this.icon = icon;
        this.glowing = false;
    }

    public Warp(String id, Location location, Material icon, boolean glowing, boolean restricted) {
        this.id = id;
        this.location = location;
        this.icon = icon;
        this.glowing = glowing;
        this.restricted = restricted;
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

    public boolean isGlowing() {
        return glowing;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
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