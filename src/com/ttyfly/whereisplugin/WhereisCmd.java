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
				sender.sendMessage(args[0] + "�ƺ������������������");
				return true;
			}
		}

		player.sendMessage(player.getName() + "λ��" + getXYZ(player));
		String[] names = RegionManager.getInstance().getNearbyRegionNames(player.getLocation());
		if (names.length != 0)
			player.sendMessage("��" + Utils.joinString(names, ", ") + "����");
		return true;
	}

	private String getXYZ(Player player) {
		Location loc = player.getLocation();
		return Utils.getWorldNameCN(player.getWorld()) + "�� (" + loc.getBlockX() + ", " + loc.getBlockY() + ", "
				+ loc.getBlockZ() + ")";
	}
	
	private boolean showHelper(CommandSender sender) {
		sender.sendMessage("��ӭʹ�� whereis �����ʹ�÷�����");
		sender.sendMessage("/whereis [me/�����] ---- ��ѯ���λ��");
		sender.sendMessage("/whereis-list [-r] ---- ��ȡ������������б�");
		return true;
	}
}
