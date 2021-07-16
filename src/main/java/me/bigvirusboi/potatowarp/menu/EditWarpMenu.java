package me.bigvirusboi.potatowarp.menu;

import me.bigvirusboi.potatowarp.Warp;
import me.bigvirusboi.potatowarp.menu.system.Menu;
import me.bigvirusboi.potatowarp.menu.system.PlayerMenuUtility;
import me.bigvirusboi.potatowarp.util.WarpUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditWarpMenu extends Menu {
    private final Warp warp;

    public EditWarpMenu(PlayerMenuUtility pmu, Warp warp) {
        super(pmu);
        this.warp = warp;
    }

    @Override
    public String getMenuName() {
        return "Edit Warp » " + warp.getId();
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;
        if (!(e.getClickedInventory().getHolder() == this)) return;
        if (e.getSlot() == 13) return;

        switch (e.getCurrentItem().getType()) {
            case RED_CONCRETE:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                new DeleteWarpMenu(pmu, warp).open();
                break;
            case BOOK:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                new WarpsMenu(pmu).open();
                break;
            case GLOW_INK_SAC:
                warp.setGlowing(!warp.isGlowing());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f);
                WarpUtils.saveWarp(warp);
                this.open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(13, WarpUtils.createWarpItem(warp, false, false));
        if (warp.isGlowing()) {
            inventory.setItem(29, makeItem(Material.GLOW_INK_SAC, "§a§lGLOWING » ON", "§7This will make the warp item glow!"));
        } else {
            inventory.setItem(29, makeItem(Material.GLOW_INK_SAC, "§c§lGLOWING » OFF", "§7This will make the warp item glow!"));
        }
        inventory.setItem(31, makeItem(Material.RED_CONCRETE, "§c§lDELETE WARP", "§7This will permanently delete this warp!"));
        inventory.setItem(36, makeItem(Material.BOOK, "§eBack"));
    }
}