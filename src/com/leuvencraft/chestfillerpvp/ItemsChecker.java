package com.leuvencraft.chestfillerpvp;

import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

public class ItemsChecker {
	public static final String MATERIAL_PATH = ".Material";
	public static final String AMOUNT_PATH = ".Amount";
	public static final String ENCHANT_PATH = ".Enchants";
	public static final String CHANCE_PATH = ".Chance";
	private static final int DEFAULT_AMOUNT = 1;
	
	private YamlConfiguration items;
	public ItemsChecker(YamlConfiguration items) {
		this.items = items;
		
	}
	

}
