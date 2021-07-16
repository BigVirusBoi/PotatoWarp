package me.bigvirusboi.potatowarp.commands;

import me.bigvirusboi.potatowarp.data.Permissions;
import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.warp.Warp;
import me.bigvirusboi.potatowarp.data.Messages;
import me.bigvirusboi.potatowarp.warp.ReplaceString;
import me.bigvirusboi.potatowarp.warp.WarpUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveWarpCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String id = args[0];
                if (PotatoWarp.getWarps().containsKey(id)) {
                    Warp warp = PotatoWarp.getWarps().get(id);
                    warp.setLocation(player.getLocation());
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                    Messages.sendMessage(player, Messages.WARP_MOVED, new ReplaceString("warp", id));
                    WarpUtils.saveWarp(warp);
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
            if (player.hasPermission(Permissions.MOVE_WARP)) {
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
