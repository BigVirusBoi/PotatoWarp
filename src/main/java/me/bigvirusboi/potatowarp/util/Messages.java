package me.bigvirusboi.potatowarp.util;

import me.bigvirusboi.potatowarp.PotatoWarp;
import org.bukkit.entity.Player;

public class Messages {
    public static final String PLAYERS_ONLY = "§cOnly players can execute this command!";

    public static final String PREFIX = PotatoWarp.getInstance().getConfig().getString("prefix");
    public static final String SPECIFY_NAME = "message.specify_warp_name";
    public static final String NAME_INVALID = "message.name_invalid";
    public static final String WARP_CREATED = "message.warp_created";
    public static final String WARP_DELETED = "message.warp_deleted";
    public static final String WARP_NOT_EXISTING = "message.warp_not_existing";
    public static final String WARP_EXISTS = "message.warp_exists";
    public static final String WARP_DELAY = "message.warp_delay";
    public static final String WARP = "message.warp";
    public static final String CANCEL_WARP = "message.cancel_warp";

    public static final String ERROR = "§cAn unexpected error occurred";

    public static void sendMessage(Player player, String name, ReplaceString... args) {
        String string = PotatoWarp.getInstance().getConfig().getString(name);
        if (string != null) {
            for (ReplaceString s : args) {
                string = string.replaceAll("%" + s.getRegex() + "%", String.valueOf(s.getReplacement()));
            }
            player.sendMessage(PREFIX + " §r" + string);
        } else player.sendMessage("§cAn unexpected error occurred");
    }
}
