package me.bigvirusboi.potatowarp.commands;

import me.bigvirusboi.potatowarp.util.Messages;
import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.util.ReplaceString;
import me.bigvirusboi.potatowarp.util.WarpUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DelWarpCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String id = args[0];
                if (PotatoWarp.getWarps().containsKey(id)) {
                    WarpUtils.deleteWarp(id);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                    Messages.sendMessage(player, Messages.WARP_DELETED, new ReplaceString("warp", id));
                } else Messages.sendMessage(player, Messages.WARP_NOT_EXISTING, new ReplaceString("warp", id));
            } else Messages.sendMessage(player, Messages.SPECIFY_NAME);
        } else {
            sender.sendMessage(Messages.PLAYERS_ONLY);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            List<String> completions = new ArrayList<>();

            Player player = (Player) sender;
            if (player.hasPermission("potatowarp.delwarp")) {
                if (args.length == 1) {
                    StringUtil.copyPartialMatches(args[0], PotatoWarp.getWarps().keySet(), completions);
                }
                Collections.sort(completions);
                return completions;
            }
        }
        return null;
    }
}
