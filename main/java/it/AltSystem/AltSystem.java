package it.AltSystem;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class AltSystem extends JavaPlugin {
    static AltSystem instance;
    private FileConfiguration config;
    public Permission perms = null;
    private File dataFile;
    private FileConfiguration dataConfig;

    @Override
    public void onEnable() {
        instance = this;
        if(!setupPermissions()){
            getLogger().severe("Vault not found, disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        getCommand("alt").setExecutor(new AltCommands());
        config = getConfig();
        saveDefaultConfig();
        createDataConfig();
        Bukkit.getPluginManager().registerEvents(new Deny(), this);
        getLogger().info("AltSystem loaded");
        getLogger().info("Version: " + getDescription().getVersion());
    }

    private boolean setupPermissions() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            perms = ((Permission) permissionProvider.getProvider());
        }
        return perms != null;
    }

    public Permission getPermission() {
        return perms;
    }

    public FileConfiguration getDataConfig() {
        return dataConfig;
    }

    private void createDataConfig() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            saveResource("data.yml", false);
        }
        dataConfig = new YamlConfiguration();
        try {
            dataConfig.load(dataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("AltSystem unloaded");
    }

    boolean alt(Player p) {
        return (p.hasPermission("AltSystem.alt") && !p.hasPermission("*") && !p.isOp());
    }

    static AltSystem getInstance() {
        return instance;
    }
}
