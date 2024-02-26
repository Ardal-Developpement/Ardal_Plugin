package org.ardal.inventories.quest.management;

import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
import org.ardal.callbacks.npc.DeleteNpcCallBack;
import org.ardal.objects.CustomNPCObj;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NpcManagementInventory extends CustomInventory implements CellCallBack {
    private final CustomNPCObj npc;
    public NpcManagementInventory(CustomNPCObj npc, Player player){
        super(npc.getNpcName() + " management:", 27, player);
        this.npc = npc;

        CellCallBack deleteNpcCB = new DeleteNpcCallBack(npc.getId());

        this.setCell(new CICell(
                this.getAdvancedPropertiesItem(),
                2, 1,
                null,
                null,
                this,
                null)
        );

        this.setCell(new CICell(
                this.getDeleteItem(),
                6, 1,
                null,
                null,
                deleteNpcCB,
                null)
        );

    }


    private ItemStack deleteItem = null;

    private ItemStack getDeleteItem(){
        if(this.deleteItem != null) { return this.deleteItem; }
        this.deleteItem = new ItemStack(Material.COBWEB);
        ItemMeta meta = this.deleteItem.getItemMeta();

        meta.setDisplayName("Delete npc");
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);

        this.deleteItem.setItemMeta(meta);
        return this.deleteItem;
    }

    private ItemStack propertiesItem = null;

    private ItemStack getAdvancedPropertiesItem(){
        if(this.propertiesItem != null) { return this.propertiesItem; }

        this.propertiesItem = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta meta = this.propertiesItem.getItemMeta();

        meta.setDisplayName("Advanced properties");
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);

        this.propertiesItem.setItemMeta(meta);
        return this.propertiesItem;
    }

    @Override
    public void onCIClick(InventoryClickEvent event){
        event.setCancelled(true);
        CICell cell = this.getCell(event.getSlot());
        if(cell != null){
            cell.onCellClick(event);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        if(item == null || !item.isSimilar(this.getAdvancedPropertiesItem())){
            return;
        }

        this.npc.onNpcManagmentClickEvent(event);
    }
}
