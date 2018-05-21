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
		c.addDefault("Abilities.Jab.Enabled", true);
		c.addDefault("Abilities.Jab.Cooldown", 3000);
		c.addDefault("Abilities.Jab.MaxUses", 3);
		
		//ChiblockJab
		c.addDefault("Combos.ChiblockJab.Enabled", true);
		c.addDefault("Combos.ChiblockJab.Cooldown", 5000);
		c.addDefault("Combos.ChiblockJav.Duration", 3000);
		
		//FlyingKick
		c.addDefault("Combos.FlyingKick.Enabled", true);
		c.addDefault("Combos.FlyingKick.Cooldown", 4000);
		c.addDefault("Combos.FlyingKick.Damage", 2);
		c.addDefault("Combos.FlyingKick.LaunchPower", 2.2);
		
		main.save();
		c = lang.get();
		
		c.addDefault("Abilities.Jab.Description", "This ability allows fast paced attacking. Left clicking will activate the ability, as will right clicking (has to be on an entity!), both adding a point to it. It can be used a certain number of times before going on cooldown.");
		c.addDefault("Combos.ChiblockJab.Description", "This combo allows you to chiblock a player. Does no extra damage!");
		c.addDefault("Combos.FlyingKick.Description", "Leap through the air, kicking entities you may encounter along the way. This combo ends when you hit the ground!");
		
		lang.save();
	}
}
