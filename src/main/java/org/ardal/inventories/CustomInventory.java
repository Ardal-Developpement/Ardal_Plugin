package org.ardal.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomInventory implements Listener, InventoryHolder {
    private static final int NB_CELL_BY_LINE = 9;
    private final String title;
    private final CISize size;
    private final List<CICell> cells;
    private final Player player;

    public CustomInventory(String title, CISize size, Player player){
        this.title = title;
        this.size = size;
        this.player = player;
        this.cells = new ArrayList<>();

        for(int i = 0; i < this.size.toInt(); i++){
            this.cells.add(new CICell(null, i));
        }
    }

    public abstract void onCIClose(InventoryCloseEvent event);

    public abstract void onCIClick(InventoryClickEvent event);

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this.player, this.getSize().toInt(), this.title);
        for(CICell cell : this.cells){
            inventory.setItem(cell.getSlot(), cell.getItem());
        }

        return inventory;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            if(event.getPlayer() == this.player
                    && event.getInventory().getHolder() instanceof CustomInventory){
                onCIClose(event);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null
                && event.getWhoClicked() == this.player
                && event.getClickedInventory().getHolder() instanceof CustomInventory) {
            onCIClick(event);
        }
    }

    public void showInventory(){
        this.player.openInventory(this.getInventory());
    }

    public void closeInventory(){
        this.player.closeInventory();
    }

    public boolean setItem(ItemStack item, int slot){
        if(this.cells.size() == this.size.toInt()){
            return false;
        }

        this.cells.get(slot).setItem(item);
        return true;
    }

    public boolean setItem(ItemStack item, int x, int y){
        int slot = y * NB_CELL_BY_LINE + x;
        if(slot > this.size.toInt()){
            return false;
        }

        this.cells.get(slot).setItem(item);
        return true;
    }

    public boolean addItem(ItemStack item){
        for(CICell cell : this.cells){
            if(cell.isEmpty()){
                cell.setItem(item);
                return true;
            }
        }

        return false;
    }

    public boolean addCell(CICell newCell){
        for(CICell cell : this.cells){
            if(cell.getSlot() == newCell.getSlot()){
                return false;
            }
        }

        return this.cells.add(newCell);
    }

    public List<CICell> getCells() {
        return cells;
    }

    public CICell getCell(int index){
        return this.cells.get(index);
    }

    public CICell getCell(int x, int y){
        int slot = y * NB_CELL_BY_LINE + x;
        if(slot > this.size.toInt()){
            return null;
        }

        return getCell(slot);
    }

    public List<ItemStack> getAllItemStack(){
        List<ItemStack> items = new ArrayList<>();
        for(CICell cell : this.cells){
            if(!cell.isEmpty()){
                items.add(cell.getItem());
            }
        }

        return items;
    }

    public CISize getSize() {
        return size;
    }

    public Player getPlayer() {
        return player;
    }


}
