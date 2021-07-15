package me.bigvirusboi.potatowarp;

public class Messages {
    public static final String PLAYERS_ONLY = "Only players can execute this command!";

    public static final String SPECIFY_WARP = "Please specify a warp name!";
    public static final String WARP_CREATED = "Warp %s created!";
    public static final String WARP_NOT_EXISTING = "Warp %s does not exist!";
    public static final String WARP_EXISTS = "Warp %s already exists!";
    public static final String WARP = "Warped to %s";

    public static final String ERROR = "§cAn unexpected error occurred";

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
