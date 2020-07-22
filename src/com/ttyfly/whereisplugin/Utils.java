package com.ttyfly.whereisplugin;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class Utils {

	public static void sendPluginMessage(CommandSender sender, String msg) {
		sender.sendMessage(ChatColor.GRAY + "Whereis " + ChatColor.RED + ">> " + ChatColor.WHITE + msg);
	}
	
	public static String getLocationString(Location loc) {
		return getWorldNameCN(loc.getWorld()) + "的 (" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")";
	}
	
	public static String getWorldNameCN(World world) {
		switch (world.getName()) {
		case "world":
			return "主世界";
		case "world_nether":
			return "地狱";
		case "world_the_end":
			return "末地";
		default:
			return world.getName();
		}
	}
	
	public static String joinString(String[] str, String by) {
		StringBuffer sb = new StringBuffer();
		sb.append(str[0]);
		for (int i = 1; i < str.length; i++) {
			sb.append(by + str[i]);
		}
		return sb.toString();
	}
	
	public static String joinString(Set<String> str, String by) {
		return joinString(str.toArray(new String[0]), by);
	}
}
