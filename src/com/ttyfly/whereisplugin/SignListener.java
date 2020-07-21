package com.ttyfly.whereisplugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

public class SignListener implements Listener {

	Pattern pattern = Pattern.compile("^#(.*)#$");

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		String name = getSignName(event.getLines());
		if (name == null)
			return;

		Location loc = event.getBlock().getLocation();
		RegionManager.getInstance().addRegion(name, loc);
		event.getPlayer().sendMessage(
				"(" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ") 附近已设定为" + name);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		BlockState block = event.getBlock().getState();
		if (!(block instanceof Sign))
			return;

		Sign sign = (Sign) block;
		String name = getSignName(sign.getLines());
		if (name == null)
			return;

		RegionManager.getInstance().deleteRegion(name, block.getLocation());
		event.getPlayer().sendMessage(name + "设定已取消");
	}

	private String getSignName(String[] lines) {
		for (String line : lines) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find())
				return matcher.group(1);
		}
		return null;
	}
}
