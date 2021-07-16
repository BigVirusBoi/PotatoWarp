package me.bigvirusboi.potatowarp.warp;

import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.Config;
import me.bigvirusboi.potatowarp.data.Messages;
import org.bukkit.*;
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
        if (!PotatoWarp.PLAYER_TIME.containsKey(player)) {
            if (Config.shouldDelayWarp()) {
                Messages.sendMessage(player, Messages.WARP_DELAY, new ReplaceString("warp", id), new ReplaceString("time", Config.WARP_DELAY));
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
        player.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, .5f, 1f);
        Bukkit.getScheduler().runTaskLater(PotatoWarp.getInstance(), () -> {
            if (Config.WARP_PARTICLES) {
                location.getWorld().spawnParticle(Particle.REVERSE_PORTAL, location, 200, 0, 0, 0, 1);
            }
        }, 10);
    }
}
