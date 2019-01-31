package me.simplicitee.chipack.configuration;

import me.simplicitee.chipack.*;
import java.io.*;
import org.bukkit.configuration.file.*;

public class Config
{
    private ChiPack plugin;
    public File file;
    public FileConfiguration config;
    
    public Config(final String name) {
        this(new File(String.valueOf(name) + ".yml"));
    }
    
    public Config(final File file) {
        this.plugin = ChiPack.get();
        this.file = new File(this.plugin.getDataFolder() + File.separator + file);
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        this.reload();
    }
    
    public void create() {
        if (!this.file.getParentFile().exists()) {
            try {
                this.file.getParentFile().mkdirs();
                this.plugin.getLogger().info("Generating new directory for " + this.file.getName() + "!");
            }
            catch (Exception e) {
                this.plugin.getLogger().info("Failed to generate directory!");
                e.printStackTrace();
            }
        }
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.plugin.getLogger().info("Generating new " + this.file.getName() + "!");
            }
            catch (Exception e) {
                this.plugin.getLogger().info("Failed to generate " + this.file.getName() + "!");
                e.printStackTrace();
            }
        }
    }
    
    public FileConfiguration get() {
        return this.config;
    }
    
    public void reload() {
        this.create();
        try {
            this.config.load(this.file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void save() {
        try {
            this.config.options().copyDefaults(true);
            this.config.save(this.file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
