package org.ardal.inventories.quest.management;

import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CISize;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
import org.ardal.callbacks.npc.DeleteNpcCallBack;
import org.ardal.objects.CustomNPCObj;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NpcManagementInventory extends CustomInventory {
    public NpcManagementInventory(CustomNPCObj npc, Player player){
        super(npc.getNpcName() + " management:", CISize.CIS_9x3, player);

        CellCallBack deleteNpcCB = new DeleteNpcCallBack(npc.getId());

        this.setCell(new CICell(
                this.getAdvancedPropertiesItem(),
                2, 1,
                null,
                null,
                null,
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

    private ItemStack getDeleteItem(){
        ItemStack deleteItem = new ItemStack(Material.COBWEB);
        ItemMeta meta = deleteItem.getItemMeta();

        meta.setDisplayName("Delete npc");
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);

        deleteItem.setItemMeta(meta);
        return deleteItem;
    }

    private ItemStack getAdvancedPropertiesItem(){
        ItemStack aPItem = new ItemStack(Material.WOODEN_PICKAXE);
        ItemMeta meta = aPItem.getItemMeta();

        meta.setDisplayName("Advanced properties");
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);

        aPItem.setItemMeta(meta);
        return aPItem;
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
    public void onCIClose(InventoryCloseEvent event) {
        this.unregisterInventory();
    }

}
