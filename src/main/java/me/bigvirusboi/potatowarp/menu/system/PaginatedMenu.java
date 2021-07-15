package me.bigvirusboi.potatowarp.menu.system;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class PaginatedMenu extends Menu {
    protected int page = 0;
    protected int maxItemsPerPage = 45;
    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public void addMenuBorder() {
        inventory.setItem(46, makeItem(Material.ARROW, ChatColor.GREEN + "Previous Page"));
        inventory.setItem(52, makeItem(Material.ARROW, ChatColor.GREEN + "Next Page"));
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }
}