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
        if (!PotatoWarp.getInstance().isWarping(player)) {
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

    // TODO when warping between worlds, no particles are shown...
    public void forceWarp(Player player) {
        Messages.sendMessage(player, Messages.WARP, new ReplaceString("warp", id));
        player.teleport(location);
        Bukkit.getScheduler().runTaskLater(PotatoWarp.getInstance(), () -> {
            Location loc = player.getLocation();
            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, .5f, 1f);
            if (Config.WARP_PARTICLES) {
                player.getWorld().spawnParticle(Particle.REVERSE_PORTAL, x, y, z, 200, 0, 0, 0, 1);
            }
        }, 1);
    }
}
