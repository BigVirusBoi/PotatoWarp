package me.bigvirusboi.potatowarp.commands;

import me.bigvirusboi.potatowarp.data.Permissions;
import me.bigvirusboi.potatowarp.data.Messages;
import me.bigvirusboi.potatowarp.PotatoWarp;
import me.bigvirusboi.potatowarp.warp.ReplaceString;
import me.bigvirusboi.potatowarp.warp.WarpUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SetWarpCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String id = args[0];
                if (id.length() <= 12) {
                    if (!PotatoWarp.getWarps().containsKey(id)) {
                        if (args.length >= 2) {
                            WarpUtils.createWarp(id, player.getLocation(), args[1]);
                        } else {
                            WarpUtils.createWarp(id, player.getLocation(), Material.CLAY_BALL);
                        }
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                        Messages.sendMessage(player, Messages.WARP_CREATED, new ReplaceString("warp", id));
                    } else Messages.sendMessage(player, Messages.WARP_EXISTS, new ReplaceString("warp", id));
                } else Messages.sendMessage(player, Messages.NAME_INVALID, new ReplaceString("warp", id));
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
            if (player.hasPermission(Permissions.SETWARP)) {
                if (args.length == 2) {
                    StringUtil.copyPartialMatches(args[1], Arrays.stream(Material.values()).map(Material::name).map(String::toLowerCase).collect(Collectors.toList()), completions);
                }
                Collections.sort(completions);
                return completions;
            }
        }
        return null;
    }
}
