package com.ttyfly.whereisplugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhereisListCmd implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return sendWorldList(sender);
		else if (args[0].equals("-r"))
			return sendRegionList(sender);
		else
			return false;
	}

	private boolean sendWorldList(CommandSender sender) {
		for (World world : Bukkit.getWorlds()) {
			List<Player> players = world.getPlayers();
			if (players.size() == 0)
				continue;
			Set<String> playerNames = new HashSet<String>();
			for (Player player : players)
				playerNames.add(player.getName());
			Utils.sendPluginMessage(sender, ChatColor.DARK_AQUA + "[" + Utils.getWorldNameCN(world) + "] " + ChatColor.WHITE + Utils.joinString(playerNames, ", "));
		}
		return true;
	}

	private boolean sendRegionList(CommandSender sender) {
		String[] names = RegionManager.getInstance().getAllRegionNames();
		if (names.length == 0) {
			Utils.sendPluginMessage(sender, "未设置区域");
			return true;
		}

		for (String name : names) {
			Player[] players = RegionManager.getInstance().getNearbyPlayers(name);
			if (players.length == 0)
				continue;
			Set<String> playerNames = new HashSet<String>();
			for (Player player : players)
				playerNames.add(player.getName());
			Utils.sendPluginMessage(sender, ChatColor.DARK_AQUA + "[" + name + "] " + ChatColor.WHITE + Utils.joinString(playerNames, ", "));
		}
		Utils.sendPluginMessage(sender, "未显示区域外玩家");
		return true;
	}
}
