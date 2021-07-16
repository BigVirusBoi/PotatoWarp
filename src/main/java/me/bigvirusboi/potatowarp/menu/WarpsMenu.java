package me.bigvirusboi.potatowarp.menu;

import me.bigvirusboi.potatowarp.util.Messages;
import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.Warp;
import me.bigvirusboi.potatowarp.util.WarpUtils;
import me.bigvirusboi.potatowarp.menu.system.Menu;
import me.bigvirusboi.potatowarp.menu.system.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class WarpsMenu extends Menu {
    private int page = 0;
    private int index = 0;

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

        if (e.getCurrentItem().getType() == Material.ARROW) {
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Previous Page")) {
                if (page != 0) {
                    page = page - 1;
                    super.open();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                }
            } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next Page")) {
                if (!((index + 1) >= PotatoWarp.getWarps().size())) {
                    page = page + 1;
                    super.open();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                }
            }
        } else {
            ItemMeta meta = e.getCurrentItem().getItemMeta();
            if (meta != null) {
                String id = meta.getPersistentDataContainer().get(new NamespacedKey(PotatoWarp.getInstance(), "id"), PersistentDataType.STRING);
                if (id != null) {
                    Warp warp = WarpUtils.getWarp(id);
                    if (warp != null) {
                        ClickType type = e.getClick();
                        if (type == ClickType.MIDDLE) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 0f);
                            new EditWarpMenu(pmu, warp).open();
                        } else if (type.isShiftClick()) {
                            warp.forceWarp(player);
                            player.closeInventory();
                        } else {
                            warp.warpPlayer(player);
                            player.closeInventory();
                        }
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0f);
                        player.sendMessage(Messages.ERROR);
                    }
                }
            }
        }
    }

    public void addMenuBorder() {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(45 + i, makeItem(Material.BLACK_STAINED_GLASS_PANE, "§r"));
        }

        inventory.setItem(49, makeItem(Material.BOOK, "§e§lINFO", "§7Click to warp", "§7Shift-Click to quick warp (skip delay)", "§7Middle click to edit"));

        if (page != 0) inventory.setItem(46, makeItem(Material.ARROW, ChatColor.GREEN + "Previous Page"));
        if ((45 * (page + 1) < PotatoWarp.getWarps().size())) inventory.setItem(52, makeItem(Material.ARROW, ChatColor.GREEN + "Next Page"));
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        List<Warp> list = new ArrayList<>(PotatoWarp.getWarps().values());

        if (!list.isEmpty()) {
            for (int i = 0; i < 45; i++) {
                index = 45 * page + i;
                if (index >= list.size()) break;

                Warp warp = list.get(i);
                inventory.addItem(WarpUtils.createWarpItem(warp, true, true));
            }
        }
    }
}