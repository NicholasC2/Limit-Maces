package com.nick.limitmaces;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class LimitCrafting implements Listener {
    private JavaPlugin plugin;
    private final File maceFile;

    public LimitCrafting(JavaPlugin plugin) {
        this.plugin = plugin;
        this.maceFile = new File(plugin.getDataFolder(), "maceCount.txt");
        setupMaceCountFile();
    }

    private void setupMaceCountFile() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!maceFile.exists() || readMaceCountInternal() == null) {
            writeMaceCount(0);
        }
    }

    public int readMaceCount() {
        Integer value = readMaceCountInternal();
        return value != null ? value : 0;
    }

    public void writeMaceCount(int count) {
        try (FileWriter writer = new FileWriter(maceFile, false)) {
            writer.write(Integer.toString(count));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer readMaceCountInternal() {
        try (Scanner scanner = new Scanner(maceFile)) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent event) {
        if(event.getRecipe() != null) {
            if(event.getRecipe().getResult() != null) {
                if(event.getRecipe().getResult().getType() == Material.MACE) {
                    if(readMaceCount() >= plugin.getConfig().getInt("max-maces")) {
                        event.getInventory().setResult(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

    @EventHandler 
    public void onCraft(CraftItemEvent event) {
        if(event.getRecipe() != null) {
            if(event.getRecipe().getResult() != null) {
                if(event.getRecipe().getResult().getType() == Material.MACE) {
                    if(event.isShiftClick()) {
                        event.setCancelled(true);
                    } else {
                        writeMaceCount(readMaceCount()+1);
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
                    if(readMaceCount() >= plugin.getConfig().getInt("max-maces")) {
                        event.setCancelled(true);
                    } else {
                        writeMaceCount(readMaceCount()+1);
                    }
                }
            }
        }
    }
}
