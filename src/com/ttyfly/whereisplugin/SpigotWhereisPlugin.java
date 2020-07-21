package com.ttyfly.whereisplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotWhereisPlugin extends JavaPlugin {

	private static SpigotWhereisPlugin instance;

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginCommand("whereis").setExecutor(new WhereisCmd());
		Bukkit.getPluginCommand("whereis-list").setExecutor(new WhereisListCmd());
		getServer().getPluginManager().registerEvents(new SignListener(), this);
		saveDefaultConfig();
		getLogger().info("Whereis plugin enabled!");
	}

	@Override
	public void onDisable() {
		saveConfig();
		getLogger().info("Whereis plugin disabled!");
	}
	
	public static SpigotWhereisPlugin getInstance() {
		return instance;
	}
}
