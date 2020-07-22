package com.ttyfly.whereisplugin;

import org.bukkit.Bukkit;
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
				Utils.sendPluginMessage(sender, args[0] + "似乎并不存在于这个世界");
				return true;
			}
		}

		Utils.sendPluginMessage(sender, player.getName() + "位于" + Utils.getLocationString(player.getLocation()));
		String[] names = RegionManager.getInstance().getNearbyRegionNames(player.getLocation());
		if (names.length != 0)
			Utils.sendPluginMessage(sender, "在" + Utils.joinString(names, ", ") + "附近");
		return true;
	}
	
	private boolean showHelper(CommandSender sender) {
		Utils.sendPluginMessage(sender, "欢迎使用 whereis 插件");
		Utils.sendPluginMessage(sender, "whereis [me/玩家名] ---- 查询玩家位置");
		Utils.sendPluginMessage(sender, "/whereis-list [-r] ---- 获取玩家所在区域列表");
		return true;
	}
}
