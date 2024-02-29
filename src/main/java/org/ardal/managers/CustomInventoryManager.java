package org.ardal.managers;

import org.ardal.Ardal;
import org.ardal.api.managers.ArdalManager;
import org.ardal.api.inventories.CustomInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;

public class CustomInventoryManager implements ArdalManager, Listener {

    private final List<CustomInventory> registeredInvsee;

    public CustomInventoryManager(){
        this.registeredInvsee = new ArrayList<>();

        Ardal.getInstance().getServer().getPluginManager().registerEvents(this, Ardal.getInstance());
    }


    public void registerInvsee(CustomInventory invsee){

        if(!this.registeredInvsee.contains(invsee)){
            this.registeredInvsee.add(invsee);
            //System.out.println("register inventory: " + this.registeredInvsee);

        }
    }

    public void unregisterInvsee(CustomInventory invsee){
        this.registeredInvsee.remove(invsee);
        //System.out.println("Unregister inventory: " + this.registeredInvsee);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(!(event.getPlayer() instanceof Player)) { return; }
        for(CustomInventory invsee : this.registeredInvsee) {
            if(invsee.getPlayer() == event.getPlayer()){
                invsee.onCIClose(event);
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null){
            return;
        }
        for(CustomInventory invsee : this.registeredInvsee){
            if(invsee.getPlayer() == event.getWhoClicked()){
                invsee.onCIClick(event);
                return;
            }
        }
    }


    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
