package com.nick.limitmaces;

import org.bukkit.plugin.java.JavaPlugin;

public class LimitMaces extends JavaPlugin {
    @Override
    public void onEnable() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        saveDefaultConfig();
        if(!getConfig().contains("max-maces")) {
            getConfig().set("max-maces", 1);
        }
        getServer().getPluginManager().registerEvents(new LimitCrafting(this), this);
        getLogger().info("Mace Limiting Enabled");
    }
}