package me.bigvirusboi.potatowarp.menu;

import me.bigvirusboi.potatowarp.Messages;
import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.Warp;
import me.bigvirusboi.potatowarp.WarpUtils;
import me.bigvirusboi.potatowarp.menu.system.PaginatedMenu;
import me.bigvirusboi.potatowarp.menu.system.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class WarpsMenu extends PaginatedMenu {
    public WarpsMenu(PlayerMenuUtility pmu) {
        super(pmu);
    }

    @Override
    public String getMenuName() {
        return "Warps";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) return;
        switch (e.getCurrentItem().getType()) {
            case CLAY_BALL:
                String id = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(PotatoWarp.getInstance(), "id"), PersistentDataType.STRING);
                Warp warp = WarpUtils.getWarp(id);
                if (warp != null) {
                    warp.warpPlayer(player);
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, .5f, 1f);
                } else {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0f);
                    player.sendMessage(Messages.ERROR);
                }
                player.closeInventory();
                break;
            case ARROW:
                if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Previous Page")) {
                    if (page != 0) {
                        page = page - 1;
                        super.open();
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                    }
                } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")) {
                    if (!((index + 1) >= PotatoWarp.WARPS.size())) {
                        page = page + 1;
                        super.open();
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                    }
                }
                break;
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        List<Warp> list = new ArrayList<>(PotatoWarp.WARPS.values());

        if (!list.isEmpty()) {
            for (int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if (index >= list.size()) break;

                Warp warp = list.get(i);
                ItemStack is = makeItem(Material.CLAY_BALL, "§b§n" + warp.getId(), "", "§7Click to warp");

                ItemMeta im = is.getItemMeta();
                im.getPersistentDataContainer().set(new NamespacedKey(PotatoWarp.getInstance(), "id"), PersistentDataType.STRING, warp.getId());
                is.setItemMeta(im);

                inventory.addItem(is);
            }
        }
    }
}