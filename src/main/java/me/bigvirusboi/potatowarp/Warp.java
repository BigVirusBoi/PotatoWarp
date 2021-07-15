package me.bigvirusboi.potatowarp;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Warp {
    private final String id;
    private final World world;
    private final Location location;

    public Warp(String id, Location location) {
        this.id = id;
        this.world = location.getWorld();
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public World getWorld() {
        return world;
    }

    public Location getLocation() {
        return location;
    }

    public void warpPlayer(Player player) {
        player.sendMessage(Messages.warped(id));
    }
}
