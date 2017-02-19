package org.serenity.maven.plugins.SerenityPlugins.Utility;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
 
@Deprecated

public class ConfigAccessor {
 
    private final String configFileName;
    private final JavaPlugin plugin;
    private final Logger logger;
    
    private File configFile;
    private FileConfiguration fileConfiguration;
 
    public ConfigAccessor(JavaPlugin plugin, String configFileName) {
        if (plugin == null) {
        	throw new IllegalArgumentException("Plugin cannot be null");        	
        
        } else if (!plugin.isEnabled()) {
        	
        	throw new IllegalArgumentException("Plugin must be initialized");
        }
        
        this.plugin = plugin;
        this.configFileName = configFileName;
        this.logger = plugin.getLogger();
        
        File pluginDataFolder = plugin.getDataFolder();
        
        if (pluginDataFolder == null) {
        	IllegalStateException error = new IllegalStateException();
        	
        	logger.log(Level.SEVERE, "No Serenity Data Folder Found", error);
        	throw error;
        }
        
        this.configFile = new File(plugin.getDataFolder(), configFileName);
    }
 
    public void reloadConfig() {        
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
 
        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource(configFileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            fileConfiguration.setDefaults(defConfig);
        }
    }
 
    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            this.reloadConfig();
        }
        return fileConfiguration;
    }
 
    public void saveConfig() {
        if (fileConfiguration == null || configFile == null) {
            return;
        } else {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
            }
        }
    }
    
    public void saveDefaultConfig() {
        if (!configFile.exists()) {            
            this.plugin.saveResource(configFileName, false);
        }
    }
 
}