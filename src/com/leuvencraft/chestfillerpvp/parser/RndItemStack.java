package com.leuvencraft.chestfillerpvp.parser;

import org.bukkit.inventory.ItemStack;
/**
 * 
 * @author Pieter Verlinden
 * @version 1.0
 */

public class RndItemStack extends ItemStack{
	private final int iD;
	private final String itemName;
	private double dropChance;
	private double accumulatedChance;
	//ArrayList<String> itemErrLog;
	/**
	 * 
	 * @param iD
	 * @param dropChance
	 * @param accumulatedChance
	 * @param name
	 * @param item
	 */
	public RndItemStack(int iD, double dropChance, double accumulatedChance,String name, ItemStack item){
		super(item);
		this.iD = iD;
		this.dropChance = dropChance;
		this.accumulatedChance = accumulatedChance;
		this.itemName = name;
		//this.itemErrLog = new ArrayList<String>();
	}
	/**
	 * @return the iD
	 */
	public int getiD() {
		return iD;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @return the getChance
	 */
	public double getDropChance() {
		return dropChance;
	}
	
	/**
	 * @return the accumulatedChance
	 */
	public double getAccumulatedChance() {
		return accumulatedChance;
	}
	/**
	 * @param accumulatedChance the accumulatedChance to set
	 */
	public void setAccumulatedChance(double accumulatedChance) {
		this.accumulatedChance = accumulatedChance;
	}
	/**
	 * @param dropChance the dropChance to set
	 */
	public void setDropChance(double dropChance) {
		this.dropChance = dropChance;
	}
	
	
 
}
