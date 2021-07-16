package me.bigvirusboi.potatowarp.menu;

import me.bigvirusboi.potatowarp.warp.Warp;
import me.bigvirusboi.potatowarp.menu.system.Menu;
import me.bigvirusboi.potatowarp.warp.WarpUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditWarpMenu extends Menu {
    private final Warp warp;

    public EditWarpMenu(Player player, Warp warp) {
        super(player);
        this.warp = warp;
    }

    @Override
    public String getName() {
        return "Edit Warp » " + warp.getId();
    }

    @Override
    public int getRows() {
        return 5;
    }

    @Override
    public void handle(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;
        if (!(e.getClickedInventory().getHolder() == this)) return;
        if (e.getSlot() == 13) return;

        switch (e.getCurrentItem().getType()) {
            case BARRIER:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                new DeleteWarpMenu(player, warp).open();
                break;
            case BOOK:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                new WarpsMenu(player).open();
                break;
            case GLOW_INK_SAC:
                warp.setGlowing(!warp.isGlowing());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f);
                WarpUtils.saveWarp(warp);
                this.open();
                break;
            case ANVIL:
                warp.setRestricted(!warp.isRestricted());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f);
                WarpUtils.saveWarp(warp);
                this.open();
                break;
        }
    }

    @Override
    public void setItems() {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, makeItem(Material.BLACK_STAINED_GLASS_PANE, "§r"));
            inventory.setItem(36 + i, makeItem(Material.BLACK_STAINED_GLASS_PANE, "§r"));
        }
        for (int i = 0; i < 27; i++) {
            inventory.setItem(9 + i, makeItem(Material.GRAY_STAINED_GLASS_PANE, "§r"));
        }

        inventory.setItem(13, WarpUtils.createWarpItem(warp, false, false));
        if (warp.isGlowing()) {
            inventory.setItem(29, makeItem(Material.GLOW_INK_SAC, "§a§lGLOWING » ON", "§7This will make the warp item glow!"));
        } else {
            inventory.setItem(29, makeItem(Material.GLOW_INK_SAC, "§c§lGLOWING » OFF", "§7This will make the warp item glow!"));
        }
        if (warp.isRestricted()) {
            inventory.setItem(33, makeItem(Material.ANVIL, "§a§lRESTRICTED » ON", "§7This will make the warp restricted!", "§7(Only admins can access)"));
        } else {
            inventory.setItem(33, makeItem(Material.ANVIL, "§c§lRESTRICTED » OFF", "§7This will make the warp restricted!", "§7(Only admins can access)"));
        }
        inventory.setItem(31, makeItem(Material.BARRIER, "§c§lDELETE WARP", "§7This will permanently delete this warp!"));
        inventory.setItem(36, makeItem(Material.BOOK, "§eBack"));
    }
}