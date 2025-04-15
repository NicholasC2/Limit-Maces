package com.nick.limitmaces;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class LimitMaces extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new MaceCount(), this);
        getServer().getPluginManager().registerEvents(new LimitCrafting(this), this);
        getLogger().info("Plugin Enabled");
    }
}