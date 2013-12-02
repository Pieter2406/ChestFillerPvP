/**
 * 
 */
package com.leuvencraft.chestfillerpvp;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Pieter Verlinden
 * @version 1.0
 *
 */
public class ChestFillerPvP extends JavaPlugin {
	private ArrayList<String> errLog;


	public void onEnable(){
		
	}
	public void onDisable(){

	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		return false;

	}
	
}
