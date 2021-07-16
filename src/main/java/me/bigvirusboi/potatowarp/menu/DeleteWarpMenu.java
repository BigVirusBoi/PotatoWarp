package me.bigvirusboi.potatowarp.menu;

import me.bigvirusboi.potatowarp.warp.Warp;
import me.bigvirusboi.potatowarp.menu.system.Menu;
import me.bigvirusboi.potatowarp.data.Messages;
import me.bigvirusboi.potatowarp.warp.ReplaceString;
import me.bigvirusboi.potatowarp.warp.WarpUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DeleteWarpMenu extends Menu {
    private final Warp warp;

    public DeleteWarpMenu(Player player, Warp warp) {
        super(player);
        this.warp = warp;
    }

    @Override
    public String getName() {
        return "Deleting Warp » " + warp.getId();
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public void handle(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;
        if (!(e.getClickedInventory().getHolder() == this)) return;
        if (e.getSlot() == 13) return;

        switch (e.getCurrentItem().getType()) {
            case RED_CONCRETE:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                WarpUtils.deleteWarp(warp.getId());
                Messages.sendMessage(player, Messages.WARP_DELETED, new ReplaceString("warp", warp.getId()));
                player.closeInventory();
                break;
            case LIME_CONCRETE:
            case BOOK:
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                new EditWarpMenu(player, warp).open();
                break;
        }
    }

    @Override
    public void setItems() {
        inventory.setItem(13, WarpUtils.createWarpItem(warp, false, false));
        inventory.setItem(11, makeItem(Material.LIME_CONCRETE, "§a§lDON'T DELETE"));
        inventory.setItem(15, makeItem(Material.RED_CONCRETE, "§c§lDELETE"));
        inventory.setItem(22, makeItem(Material.BOOK, "§eBack"));
    }
}