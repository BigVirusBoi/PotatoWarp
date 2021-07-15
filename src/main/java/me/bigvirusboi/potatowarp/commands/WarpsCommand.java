package me.bigvirusboi.potatowarp.commands;

import me.bigvirusboi.potatowarp.Messages;
import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.menu.WarpsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            new WarpsMenu(PotatoWarp.getPMU(player)).open();
        } else {
            sender.sendMessage(Messages.PLAYERS_ONLY);
        }
        return true;
    }
}
