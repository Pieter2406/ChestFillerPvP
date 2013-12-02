package com.leuvencraft.chestfillerpvp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 
 * @author Pieter Verlinden
 * @version 1.0
 *
 */
public class CheckLayer {
	
	
	private boolean hasErrors;
	
	private ItemsChecker itemsChecker;
	
	private JavaPlugin sourcePlugin;
	
	private ArrayList<String> errLog;

	private File[] chestConfigFiles;
	private File itemsFile;
	private File chestsFile;
	private File defaultChestConfig;

	private List<YamlConfiguration> chestConfigList;
	private YamlConfiguration items;
	private YamlConfiguration chestlist;
	
	public CheckLayer(JavaPlugin sourcePlugin) {
		this.sourcePlugin = sourcePlugin;
		errLog = new ArrayList<String>();
		try {
			firstRun();
		} catch (Exception e) {
			this.addErrMsg("Error occured in creating default files");
		}
		chestConfigList = loadChestConfigurations();
		try{
			items.load(itemsFile);
			chestlist.load(chestsFile);
		}catch (Exception ex){
			this.addErrMsg("An error occurred during loading items.yml and chests.yml");
		}
		sourcePlugin.saveDefaultConfig();
		itemsChecker = new ItemsChecker(items);
		
	}
	
	

	/*
	 * this copy(); method copies the specified file from your jar
	 *     to your /plugins/<pluginName>/ folder
	 */
	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while((len=in.read(buf))>0){
				out.write(buf,0,len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Creates the main items file if it doesn't exist.
		private void firstRun() throws Exception {
			if(!itemsFile.exists()){
				itemsFile.getParentFile().mkdirs();
				copy(this.sourcePlugin.getResource("items.yml"), itemsFile);
			}
			if(!chestsFile.exists()){
				chestsFile.getParentFile().mkdirs();
				copy(this.sourcePlugin.getResource("chests.yml"), chestsFile);
			}
			if(!defaultChestConfig.exists()){
				defaultChestConfig.getParentFile().mkdirs();
				copy(this.sourcePlugin.getResource("/chestconfigs/defaultconfig.yml"), defaultChestConfig);
			}
		}

	
	//Loads the chestconfigurations.
		private ArrayList<YamlConfiguration> loadChestConfigurations(){
			try{
				File chestFolder = new File(this.sourcePlugin.getDataFolder(), "chestconfigs");
				chestConfigFiles = chestFolder.listFiles();
				ArrayList <YamlConfiguration> configs = new ArrayList<YamlConfiguration>();

				for(File file : chestConfigFiles){
					YamlConfiguration ymlConfig = YamlConfiguration.loadConfiguration(file);
					configs.add(ymlConfig);
				}
				return configs;
			}catch(Exception ex){
				addErrMsg("An error occurred at loading the chestconfigurations!");
				return null;
			}
		}
		
		public void addErrMsg(String msg){
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Date now = new Date();
			String strDate = dateFormat.format(now);
			errLog.add(errLog.size() + " :[" + strDate + "]: " + msg);
			this.hasErrors = true;
		}
		

}
