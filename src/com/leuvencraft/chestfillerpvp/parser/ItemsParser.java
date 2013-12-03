package com.leuvencraft.chestfillerpvp.parser;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

public class ItemsParser {
	public static final String MATERIAL_PATH = ".Material";
	public static final String AMOUNT_PATH = ".Amount";
	public static final String ENCHANT_PATH = ".Enchants";
	public static final String CHANCE_PATH = ".Chance";
	private ArrayList<String> itmErrLog;
	private Set<ItemSet> itemSets;
	public ItemsParser(YamlConfiguration items) {
		this.itmErrLog = new ArrayList<String>();
		for(String str : items.getKeys(false)){
			ItemSet tempItmSet = new ItemSet(items, str);
			itemSets.add(tempItmSet);
			itmErrLog.addAll(tempItmSet.getErrLog());
		}
	}
	
	
	public List<String> getItmErrLog(){
		return itmErrLog;
	}
	
	
	public HashMap<String,ItemSet> getItemSets(){
		HashMap<String,ItemSet> map = new HashMap<String,ItemSet>();
		for(ItemSet itmSet : this.itemSets){
			map.put(itmSet.itemSetName(), itmSet);
		}
		return map;
	}


	public void terminate() {
		this.itemSets = null;
	}
}
