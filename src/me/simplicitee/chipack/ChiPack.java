package me.simplicitee.chipack;

import org.bukkit.plugin.java.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import me.simplicitee.chipack.configuration.*;
import com.projectkorra.projectkorra.ability.*;

public class ChiPack extends JavaPlugin
{
    public static ChiPack plugin;
    
    public void onEnable() {
        ChiPack.plugin = this;
        this.getServer().getPluginManager().registerEvents((Listener)new ChiPackListener(), (Plugin)this);
        new ConfigHandler();
        CoreAbility.registerPluginAbilities((JavaPlugin)this, "me.simplicitee.chipack.abilities");
    }
    
    public static ChiPack get() {
        return ChiPack.plugin;
    }
}
