package me.simplicitee.chipack;

import org.bukkit.plugin.java.JavaPlugin;

import com.projectkorra.projectkorra.ability.CoreAbility;

import me.simplicitee.chipack.configuration.ConfigHandler;

public class ChiPack extends JavaPlugin{
	
	public static ChiPack plugin;

	@Override
	public void onEnable() {
		plugin = this;
		
		getServer().getPluginManager().registerEvents(new ChiPackListener(), this);
		new ConfigHandler();
		CoreAbility.registerPluginAbilities(this, "me.simplicitee.chipack.abilities");
	}
	
	public static ChiPack get() {
		return plugin;
	}
}
