package me.bigvirusboi.potatowarp;

public class Messages {
    public static final String PLAYERS_ONLY = "Only players can execute this command!";

    public static final String SPECIFY_WARP = "Please specify a warp!";
    public static final String WARP_NOT_EXISTING = "%s does not exist!";
    public static final String WARP = "Warped to %s";

    public static String warpDoesntExist(String name) {
        return String.format(WARP_NOT_EXISTING, name);
    }
    public static String warped(String name) {
        return String.format(WARP, name);
    }
}
