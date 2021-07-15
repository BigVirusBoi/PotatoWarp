package me.bigvirusboi.potatowarp.commands;

import me.bigvirusboi.potatowarp.Messages;
import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.WarpUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String id = args[0];
                if (id.length() <= 12) {
                    if (!PotatoWarp.getWarps().containsKey(id)) {
                        WarpUtils.createWarp(id, player.getLocation());
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                        player.sendMessage(Messages.warpCreated(id));
                    } else sender.sendMessage(Messages.warpExists(id));
                } else sender.sendMessage(Messages.nameInvalid(id));
            } else sender.sendMessage(Messages.SPECIFY_NAME);
        } else {
            sender.sendMessage(Messages.PLAYERS_ONLY);
        }
        return true;
    }
}
