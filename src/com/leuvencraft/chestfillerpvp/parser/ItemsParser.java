package com.leuvencraft.chestfillerpvp.parser;



import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

public class ItemsParser {
	public static final String MATERIAL_PATH = ".Material";
	public static final String AMOUNT_PATH = ".Amount";
	public static final String ENCHANT_PATH = ".Enchants";
	public static final String CHANCE_PATH = ".Chance";
	private static final int DEFAULT_AMOUNT = 1;
	private Set<Set<String>> itemSets;
	private YamlConfiguration items;
	public ItemsParser(YamlConfiguration items) {
		this.items = items;
		for(String str : items.getKeys(false)){
			itemSets.add(items.getConfigurationSection(str).getKeys(false));
		}
	}
	

}
