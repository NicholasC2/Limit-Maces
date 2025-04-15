package com.nick.limitmaces;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class LimitCrafting implements Listener {
    private JavaPlugin plugin;
    public MaceCount maceCount = new MaceCount();

    public LimitCrafting(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        if(event.getRecipe() != null) {
            if(event.getRecipe().getResult() != null) {
                if(event.getRecipe().getResult().getType() == Material.MACE) {
                    if(maceCount.getMaceCount(plugin.getServer()) >= plugin.getConfig().getInt("max-maces")) {
                        event.getInventory().setResult(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }
    @EventHandler
    public void onCrafterCraft(CrafterCraftEvent event) {
        if(event.getRecipe() != null) {
            if(event.getRecipe().getResult() != null) {
                if(event.getRecipe().getResult().getType() == Material.MACE) {
                    if(maceCount.getMaceCount(plugin.getServer()) >= plugin.getConfig().getInt("max-maces")) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
