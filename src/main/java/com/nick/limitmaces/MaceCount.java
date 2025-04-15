package com.nick.limitmaces;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BundleMeta;

public class MaceCount implements Listener {
    public int getMaceCount(Server server) {
        int maceCount = 0;
        Material mace = Material.MACE;

        for (World world : server.getWorlds()) {
            for (Chunk chunk : world.getLoadedChunks()) {
                for (BlockState state : chunk.getTileEntities()) {
                    if(state instanceof Container) {
                        maceCount += countItemsInItemStackList(((Container)state).getInventory().getContents(), mace);
                    }
                }
            }

            for (Entity entity : world.getEntities()) {
                if (entity instanceof LivingEntity && !(entity instanceof InventoryHolder)) {
                    LivingEntity living = (LivingEntity) entity;
            
                    ItemStack mainHand = living.getEquipment().getItemInMainHand();
                    ItemStack offHand = living.getEquipment().getItemInOffHand();
                    
                    if(mainHand.getType() == mace) {
                        maceCount++;
                    }
                    if(offHand.getType() == mace) {
                        maceCount++;
                    }
                }
                if (entity instanceof InventoryHolder) {
                    InventoryHolder holder = (InventoryHolder) entity;
                    Inventory inventory = holder.getInventory();

                    maceCount += countItemsInItemStackList(inventory.getContents(), mace);
                }
                if(entity instanceof ItemFrame) {
                    if(((ItemFrame)entity).getItem() != null) {
                        if(((ItemFrame)entity).getItem().getType() == mace) {
                            maceCount++;
                        }
                    }
                }
                if(entity instanceof Item) {
                    if(((Item)entity).getItemStack().getType() == mace) {
                        maceCount++;
                    }
                }
                if(entity instanceof Player) {
                    maceCount += countItemsInItemStackList(((Player) entity).getEnderChest().getContents(), mace);
                }
            }
        }

        return maceCount;
    }

    public int countItemsInItemStackList(ItemStack[] items, Material itemType) {
        int count = 0;
        for(ItemStack item : items) {
            if(item != null) {
                if(item.getItemMeta() instanceof BlockStateMeta) {
                    BlockStateMeta im = (BlockStateMeta)item.getItemMeta();
                    if(im.getBlockState() instanceof ShulkerBox){
                        ShulkerBox shulker = (ShulkerBox) im.getBlockState();
                        for(ItemStack item_in : shulker.getInventory().getContents()) {
                            if(item_in != null) {
                                if(item_in.getType() == itemType) {
                                    count++;
                                }
                            }
                        }
                    } else if(im.getBlockState() instanceof BundleMeta) {
                        BundleMeta bundle = (BundleMeta) im.getBlockState();
                        for (ItemStack containerItem : bundle.getItems()) {
                            if(containerItem.getType() == itemType) {
                                count++;
                            }
                        }
                    }
                } else {
                    if(item.getType() == itemType) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    @EventHandler
    public void giveItem(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Allay) {
            Player player = event.getPlayer();
            if(player.getInventory().getItemInMainHand() != null) {
                if(player.getInventory().getItemInMainHand().getType() == Material.MACE) {
                    event.setCancelled(true);
                }
            }
            if(player.getInventory().getItemInOffHand() != null) {
                if(player.getInventory().getItemInOffHand().getType() == Material.MACE) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
