package me.bigvirusboi.potatowarp;

public class Messages {
    public static final String PLAYERS_ONLY = "§cOnly players can execute this command!";

    public static final String SPECIFY_NAME = "§cPlease specify a warp name!";
    public static final String NAME_INVALID = "§cName §e§n%s§c is invalid!";
    public static final String WARP_CREATED = "§aWarp §e§n%s§a created!";
    public static final String WARP_DELETED = "§aWarp §e§n%s§a deleted!";
    public static final String WARP_NOT_EXISTING = "§cWarp §e§n%s§c does not exist!";
    public static final String WARP_EXISTS = "§cWarp §e§n%s§c already exists!";
    public static final String WARP = "§bWarped to §e§n%s";

    public static final String ERROR = "§cAn unexpected error occurred";

    public static String nameInvalid(String name) {
        return String.format(NAME_INVALID, name);
    }
    public static String warpDeleted(String name) {
        return String.format(WARP_DELETED, name);
    }
    public static String warpCreated(String name) {
        return String.format(WARP_CREATED, name);
    }
    public static String warpDoesntExist(String name) {
        return String.format(WARP_NOT_EXISTING, name);
    }
    public static String warpExists(String name) {
        return String.format(WARP_EXISTS, name);
    }
    public static String warped(String name) {
        return String.format(WARP, name);
    }
}
