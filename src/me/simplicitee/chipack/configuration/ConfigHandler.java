package me.simplicitee.chipack.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {

	private static Config main;
	private static Config lang;
	
	public ConfigHandler() {
		main = new Config("config");
		lang = new Config("lang");
		init();
	}
	
	public static FileConfiguration getConfig() {
		return main.get();
	}
	
	public static FileConfiguration getLang() {
		return lang.get();
	}
	
	public void init() {
		FileConfiguration c = main.get();
		
		//Jab
		c.addDefault("Abilities.Jab.Cooldown", 3000);
		c.addDefault("Abilities.Jab.MaxUses", 3);
		
		//ChiblockJab
		c.addDefault("Combos.ChiblockJab.Cooldown", 5000);
		
		//FlyingKick
		c.addDefault("Combos.FlyingKick.Cooldown", 4000);
		c.addDefault("Combos.FlyingKick.Damage", 2);
		c.addDefault("Combos.FlyingKick.LaunchPower", 2.2);
	}
}
