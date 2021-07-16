package me.bigvirusboi.potatowarp.menu;

import me.bigvirusboi.potatowarp.Warp;
import me.bigvirusboi.potatowarp.menu.system.Menu;
import me.bigvirusboi.potatowarp.menu.system.PlayerMenuUtility;
import me.bigvirusboi.potatowarp.util.WarpUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

// TODO
public class EditWarpMenu extends Menu {
    private final Warp warp;

    public EditWarpMenu(PlayerMenuUtility pmu, Warp warp) {
        super(pmu);
        this.warp = warp;
    }

    @Override
    public String getMenuName() {
        return "Edit Warp §7» §3" + warp.getId();
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(13, WarpUtils.createWarpItem(warp, false, false));
        inventory.setItem(31, makeItem(Material.RED_CONCRETE, "§c§lDELETE WARP", "§7This will permanently delete this warp!"));
        inventory.setItem(40, makeItem(Material.BOOK, "§", "§7This will permanently delete this warp!"));
    }
}