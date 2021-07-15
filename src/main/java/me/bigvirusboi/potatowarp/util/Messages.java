package me.bigvirusboi.potatowarp.util;

import me.bigvirusboi.potatowarp.PotatoWarp;

public class Messages {
    public static final String PLAYERS_ONLY = "§cOnly players can execute this command!";

    public static final String PREFIX = PotatoWarp.getInstance().getConfig().getString("prefix");
    public static final String SPECIFY_NAME = "message.specify_warp_name";
    public static final String NAME_INVALID = "message.name_invalid";
    public static final String WARP_CREATED = "message.warp_created";
    public static final String WARP_DELETED = "message.warp_deleted";
    public static final String WARP_NOT_EXISTING = "message.warp_not_existing";
    public static final String WARP_EXISTS = "message.warp_exists";
    public static final String WARP = "message.warp";

    public static final String ERROR = "§cAn unexpected error occurred";

    public static String getConfigMessage(String name, Object... args) {
        String string = PotatoWarp.getInstance().getConfig().getString(name);
        if (string != null) {
            return String.format(PREFIX + " §r" + string, args);
        }
        return "§cAn error occurred, please check the config!";
    }
}
