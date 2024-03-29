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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CustomInventory implements InventoryHolder {
    private static final int NB_CELL_BY_LINE = 9;
    private final String title;
    private final int size;
    private final List<CICell> cells;
    private final Player player;
    private final Inventory inventory;

    public CustomInventory(String title, int size, Player player){
        this.title = title;
        this.size = size;
        this.player = player;
        this.cells = new ArrayList<>(Collections.nCopies(this.size, null));
        this.inventory = Bukkit.createInventory(this.player, this.size, this.title);

        this.registerInventory();
    }

    public void onCIClose(InventoryCloseEvent event){
        this.unregisterInventory();
    }

    public void onCIClick(InventoryClickEvent event){
        CICell cell = this.getCell(event.getSlot());
        if(cell != null){
            cell.onCellClick(event);
        }
    }

    @Override
    public Inventory getInventory() {
        for(int i = 0; i < this.size; i++){
            CICell cell = this.cells.get(i);
            if(cell == null){
                this.inventory.setItem(i, null);
            } else {
                this.inventory.setItem(i, cell.getItem());
            }
        }

        return inventory;
    }

    public void showInventory(){
        this.player.openInventory(this.getInventory());
    }

    public void closeInventory(){
        this.unregisterInventory();
        this.player.closeInventory();
    }

    public boolean setCell(CICell cell){

        if(cell.getSlot() < this.cells.size()){
            this.cells.set(cell.getSlot(), cell);
            this.inventory.setItem(cell.getSlot(), cell.getItem());
            return true;
        }

        return false;
    }

    public void clearCell(int slot){
        if(slot < this.cells.size()){
            this.cells.set(slot, null);
            this.inventory.setItem(slot, null);
        }
    }

    public boolean addItem(ItemStack item){
        for(int i = 0; i < this.size; i++){
            if(this.cells.get(i) == null){
                if(this.setCell(new CICell(item, i))){
                    this.inventory.setItem(i, item);
                    return true;
                }

                return false;
            }
        }

        return false;
    }

    public List<CICell> getCells() {
        return cells;
    }

    @Nullable
    public CICell getCell(int slot){
        return this.cells.size() < slot ? null : this.cells.get(slot);
    }

    public CICell getCell(int x, int y){
        return this.getCell(y * NB_CELL_BY_LINE + x);
    }

    public List<ItemStack> getAllItemStack(){
        List<ItemStack> items = new ArrayList<>();

        for(ItemStack item : this.inventory.getContents()){
            if(item != null) { items.add(item); }
        }

        return items;
    }

    public int getSize() {
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
