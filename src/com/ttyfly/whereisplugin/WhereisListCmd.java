package com.ttyfly.whereisplugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhereisListCmd implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return defaultList(sender);
		else if (args[0].equals("-r"))
			return detailedList(sender);
		else
			return false;
	}

	private boolean defaultList(CommandSender sender) {
		for (World world : Bukkit.getWorlds()) {
			List<Player> players = world.getPlayers();
			if (players.size() == 0)
				continue;
			StringBuffer msg = new StringBuffer();
			msg.append("[" + Utils.getWorldNameCN(world) + "]");
			for (Player player : players)
				msg.append(" " + player.getName());
			sender.sendMessage(msg.toString());
		}
		return true;
	}

	private boolean detailedList(CommandSender sender) {
		String[] names = RegionManager.getInstance().getAllRegionNames();
		if (names.length == 0) {
			sender.sendMessage("未设置区域");
			return true;
		}

		for (String name : names) {
			Player[] players = RegionManager.getInstance().getNearbyPlayers(name);
			if (players.length == 0)
				continue;
			Set<String> playerNames = new HashSet<String>();
			for (Player player : players)
				playerNames.add(player.getName());
			sender.sendMessage("[" + name + "] " + Utils.joinString(playerNames, " "));
		}
		sender.sendMessage("区域外玩家未显示");
		return true;
	}
}
