package org.ardal.api.inventories;

import org.ardal.Ardal;
import org.ardal.managers.CustomInventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomInventory implements InventoryHolder {
    private static final int NB_CELL_BY_LINE = 9;
    private final String title;
    private final CISize size;
    private final List<CICell> cells;
    private final Player player;
    private final Inventory inventory;

    public CustomInventory(String title, CISize size, Player player){
        this.title = title;
        this.size = size;
        this.player = player;
        this.cells = new ArrayList<>();
        this.inventory = Bukkit.createInventory(this.player, this.getSize().toInt(), this.title);

        for(int i = 0; i < this.size.toInt(); i++){
            this.cells.add(new CICell(this.inventory, i));
        }

        this.registerInventory();
    }

    public abstract void onCIClose(InventoryCloseEvent event);

    public abstract void onCIClick(InventoryClickEvent event);

    @Override
    public Inventory getInventory() {
        return inventory;
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

    public void registerInventory(){
        Ardal.getInstance().getManager(CustomInventoryManager.class).registerInvsee(this);
    }

    public void unregisterInventory(){
        Ardal.getInstance().getManager(CustomInventoryManager.class).unregisterInvsee(this);
    }
}
