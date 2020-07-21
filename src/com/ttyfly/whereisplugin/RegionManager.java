package com.ttyfly.whereisplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RegionManager {

	private int distanceSquared;
	private FileConfiguration config;

	private static RegionManager instance = null;

	private RegionManager() {
		config = SpigotWhereisPlugin.getInstance().getConfig();
		int distance = config.getInt("distance");
		distanceSquared = distance * distance;
	}

	public static RegionManager getInstance() {
		if (instance == null)
			instance = new RegionManager();
		return instance;
	}

	public void addRegion(String name, Location location) {
		config.set("regions." + name + "." + locHash(location), location);
		saveRegions();
	}

	public void deleteRegion(String name, Location location) {
		config.set("regions." + name + "." + locHash(location), null);
		if (config.getConfigurationSection("regions." + name).getKeys(false).size() == 0)
			config.set("regions." + name, null);
		saveRegions();
	}

	public void saveRegions() {
		SpigotWhereisPlugin.getInstance().saveConfig();
	}

	public String[] getNearbyRegionNames(Location location) {
		List<String> names = new ArrayList<String>();

		ConfigurationSection regions = config.getConfigurationSection("regions");
		if (regions == null)
			return new String[0];

		ConfigurationSection signs;
		for (String name : regions.getKeys(false)) {
			signs = regions.getConfigurationSection(name);
			for (String sign : signs.getKeys(false)) {
				try {
					if (location.distanceSquared(signs.getLocation(sign)) < distanceSquared) {
						names.add(name);
						break;
					}
				} catch (IllegalArgumentException e) {
				}
			}
		}
		return names.toArray(new String[0]);
	}

	public String[] getAllRegionNames() {
		ConfigurationSection regions = config.getConfigurationSection("regions");
		if (regions == null)
			return new String[0];
		return regions.getKeys(false).toArray(new String[0]);
	}

	public Player[] getNearbyPlayers(String name) throws IllegalArgumentException {
		List<Player> players = new ArrayList<Player>();
		ConfigurationSection signs = config.getConfigurationSection("regions." + name);
		if (signs == null)
			throw new IllegalArgumentException("No such region");
		for (String sign : signs.getKeys(false)) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				try {
					if (player.getLocation().distanceSquared(signs.getLocation(sign)) < distanceSquared)
						players.add(player);
				} catch (IllegalArgumentException e) {
				}
			}
		}
		return players.toArray(new Player[0]);
	}

	private String locHash(Location loc) {
		return loc.getBlockX() + "/" + loc.getBlockY() + "/" + loc.getBlockZ();
	}
}
