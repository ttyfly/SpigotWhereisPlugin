package com.ttyfly.whereisplugin;

import java.util.Set;

import org.bukkit.World;

public class Utils {

	public static String getWorldNameCN(World world) {
		switch (world.getName()) {
		case "world":
			return "������";
		case "world_nether":
			return "����";
		case "world_the_end":
			return "ĩ��";
		default:
			return world.getName();
		}
	}
	
	public static String joinString(String[] names, String by) {
		StringBuffer sb = new StringBuffer();
		sb.append(names[0]);
		for (int i = 1; i < names.length; i++) {
			sb.append(by + names[i]);
		}
		return sb.toString();
	}
	
	public static String joinString(Set<String> names, String by) {
		return joinString(names.toArray(new String[0]), by);
	}
}
