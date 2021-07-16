package me.bigvirusboi.potatowarp;

import me.bigvirusboi.potatowarp.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Warp {
    private final String id;
    private final Location location;

    public Warp(String id, Location location) {
        this.id = id;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void warpPlayer(Player player) {
        if (!PotatoWarp.getInstance().isWarping(player)) {
            if (Config.shouldDelayWarp()) {
                Messages.sendMessage(player, Messages.WARP_DELAY, id, Config.warpDelay());
                PotatoWarp.getInstance().addWarpingPlayer(player, this);
            } else {
                forceWarp(player);
            }
        } else {
            Messages.sendMessage(player, Messages.CANCEL_WARP);
        }
    }

    public void forceWarp(Player player) {
        Messages.sendMessage(player, Messages.WARP, id);
        player.teleport(location);
        Bukkit.getScheduler().runTaskLater(PotatoWarp.getInstance(), () -> player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, .5f, 1f), 1);
    }
}
