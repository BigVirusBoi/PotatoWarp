package me.bigvirusboi.potatowarp.data;

import org.bukkit.permissions.Permission;

public class Permissions {
    public static final Permission WARP = permission("warp");
    public static final Permission WARP_RESTRICTED = permission("warp.restricted");
    //public static final Permission WARPS = permission("warps");
    public static final Permission CREATE_WARP = permission("setwarp");
    public static final Permission DELETE_WARP = permission("delwarp");
    public static final Permission MOVE_WARP = permission("movewarp");
    public static final Permission EDIT_WARP = permission("editwarp");
    public static final Permission QUICK_WARP = permission("quickwarp");

    private static Permission permission(String path) {
        return new Permission("potatowarp." + path);
    }
}
