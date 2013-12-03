package com.leuvencraft.chestfillerpvp.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemSet {

	public static final String MATERIAL_PATH = ".Material";
	//public static final String META_PATH = ".Meta";//TODO
	public static final String AMOUNT_PATH = ".Amount";
	//public static final String DISPLAY_PATH = ".Display";//TODO
	public static final String ENCHANT_PATH = ".Enchants";
	//public static final String TAGS_PATH = ".Tags";//TODO
	public static final String CHANCE_PATH = ".Chance";
	private static final int DEFAULT_AMOUNT = 1;

	private ArrayList<String> acceptableEnchants;
	
	private final String itemSetPath;

	private List<String> setErrLog;
	private FileConfiguration itemConfig;
	private Set<RndItemStack> itemsInConfig;
	private double accumulatedChance;

	public ItemSet(FileConfiguration itemConfig, String setPath){
		this.itemConfig = itemConfig;
		this.itemsInConfig = new HashSet<RndItemStack>();
		this.setErrLog = new ArrayList<String>();
		this.accumulatedChance = 0;
		this.itemSetPath = setPath;
		for(Enchantment ench : Enchantment.values()){
			acceptableEnchants.add(ench.getName());
		}
		parse();
	}

	private void parse(){
		int ID = 0;
		for(String itm : itemConfig.getConfigurationSection(itemSetPath).getKeys(false)){
			RndItemStack rndItem;
			Material mat;
			double dropChance = 0;
			int amount = DEFAULT_AMOUNT;
			HashMap<Enchantment,Integer> enchants = new HashMap<Enchantment,Integer>();
			//Check material in file. If material is not valid the file can't be made

			try{
				mat = getMaterial(itm);
			}catch(Exception ex){
				addErrMsg(String.format("Error in the material of itemset  '%s' in item '%s'.", this.itemSetPath, itm));
				continue;
			}
			//Check amount in file
			try{
				amount = itemSetConfig().getInt(itm + ItemsParser.AMOUNT_PATH);
			}catch(Exception ex){
				addErrMsg(String.format("Error in the amount of itemset '%s' in itm '%s'.", this.itemSetPath,itm));
				continue;
			}
			
			//Check enchants
			try{
				if(hasValidEnchants(itm)){
					List<String> enchantlist = itemSetConfig().getStringList(itm + ItemsParser.ENCHANT_PATH);
				}
				
			}catch
			
				List<String> enchantlist = itemConfig.getStringList(itm + ItemsParser.ENCHANT_PATH);
				for(String str : enchantlist){
					String[] enchAndLvl = str.split(":");
					enchants.put(Enchantment.getByName(enchAndLvl[0]), Integer.parseInt(enchAndLvl[1]));
				}
			}else{
				checker.addDebug("No Amount given in item: " + itm);

			}
			//Check chance
			if(checker.hasValidChance(itm)){
				dropChance = itemConfig.getDouble(itm + ItemsParser.CHANCE_PATH);
			}
			//Check accumulatedChance
			accumulatedChance += dropChance;

			rndItem = new RndItemStack(ID++,dropChance,accumulatedChance,itm, new ItemStack(mat, amount));
			try{
				rndItem.addEnchantments(enchants);
			}catch (Exception ex){
				this.checker.addErrorMsg("Enchantment cannot be put on item!");
				continue;
			}

			itemsInConfig.add(rndItem);

			for(RndItemStack item : getItemsInConfig()){
				getValidator().addDebug(item.toString());
			}
		}
	}

	private Material getMaterial(String item){
		return Material.matchMaterial(itemSetConfig().getString(item + ItemsParser.MATERIAL_PATH));
	}
	
	private ConfigurationSection itemSetConfig(){
		return itemConfig.getConfigurationSection(this.itemSetPath);
	}

	/**
	 * @return the itemsInConfig
	 */
	public Set<RndItemStack> getItemsInConfig() {
		return itemsInConfig;
	}


	public double getAccumulatedChance(){
		return this.accumulatedChance;
	}
	
	private void addErrMsg(String msg){
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		String strDate = dateFormat.format(now);
		setErrLog.add(setErrLog.size() + " :[" + strDate + "]: " + msg);
	}
	
	public boolean hasValidEnchants(String itemName){
		if(isInYmlStructure(itemName,ItemsParser.ENCHANT_PATH)){
			List<String> enchantlist = itemSetConfig().getStringList(itemName + ItemsParser.ENCHANT_PATH);
			if(!enchantlist.isEmpty()){
				for(String str : enchantlist){
					String[] enchAndLvl = str.split(":");
					if(!isValidEnchantment(enchAndLvl[0],  Integer.parseInt(enchAndLvl[1]))){
						addErrMsg(str + " is not a valid enchant in item " + itemName + ". Check the name and the lvl of the enchant.++");
						return false;
					}
				}
				return true;
			}
		}
		return false;
		
	}
	private boolean isInYmlStructure(String rootName, String toCheck){
		if(itemSetConfig().contains(rootName + toCheck)){
			return true;
		}
		return false;
	}
	private boolean isValidEnchantment(String enchantToCheck, int lvl){
		if(acceptableEnchants.contains(enchantToCheck)&& Enchantment.getByName(enchantToCheck).getMaxLevel() >= lvl){
			return true;
		}else{
			return false;
		}
	}
}

