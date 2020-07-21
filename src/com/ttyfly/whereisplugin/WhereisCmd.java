package com.ttyfly.whereisplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhereisCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return showHelper(sender);

		Player player;
		if (args[0].equals("me") && sender instanceof Player) {
			player = (Player) sender;
		} else {
			player = Bukkit.getPlayerExact(args[0]);
			if (player == null) {
				sender.sendMessage(args[0] + "似乎并不存在于这个世界");
				return true;
			}
		}

		player.sendMessage(player.getName() + "位于" + getXYZ(player));
		String[] names = RegionManager.getInstance().getNearbyRegionNames(player.getLocation());
		if (names.length != 0)
			player.sendMessage("在" + Utils.joinString(names, ", ") + "附近");
		return true;
	}

	private String getXYZ(Player player) {
		Location loc = player.getLocation();
		return Utils.getWorldNameCN(player.getWorld()) + "的 (" + loc.getBlockX() + ", " + loc.getBlockY() + ", "
				+ loc.getBlockZ() + ")";
	}
	
	private boolean showHelper(CommandSender sender) {
		sender.sendMessage("欢迎使用 whereis 插件，使用方法：");
		sender.sendMessage("/whereis [me/玩家名] ---- 查询玩家位置");
		sender.sendMessage("/whereis-list [-r] ---- 获取玩家所在区域列表");
		return true;
	}
}
