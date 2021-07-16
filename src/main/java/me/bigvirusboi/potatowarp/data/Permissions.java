package me.bigvirusboi.potatowarp.data;

import org.bukkit.permissions.Permission;

public class Permissions {
    public static final Permission WARP = permission("warp");
    public static final Permission WARP_RESTRICTED = permission("warp.restricted");
    public static final Permission WARPS = permission("warps");
    public static final Permission SETWARP = permission("setwarp");
    public static final Permission DELWARP = permission("delwarp");
    public static final Permission MOVEWARP = permission("movewarp");

    private static Permission permission(String path) {
        return new Permission("potatowarp." + path);
    }
}
