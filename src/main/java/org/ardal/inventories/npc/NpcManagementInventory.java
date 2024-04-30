package org.ardal.inventories.npc;

import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
import org.ardal.objects.NpcObj;
import org.ardal.utils.PromptUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NpcManagementInventory extends CustomInventory implements CellCallBack {

    private final NpcObj npc;
    public NpcManagementInventory(NpcObj npc, Player player){
        super(npc.getName() + " management:", 27, player);
        this.npc = npc;

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
                this,
                null)
        );

        this.setCell(new CICell(
                this.getUuidItemItem(),
                4, 1,
                null,
                null,
                this,
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

    private ItemStack uuidItem = null;

    private ItemStack getUuidItemItem(){
        if(this.uuidItem != null) { return this.uuidItem; }

        this.uuidItem = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = this.uuidItem.getItemMeta();

        meta.setDisplayName("Get npc uuid.");

        this.uuidItem.setItemMeta(meta);
        return this.uuidItem;
    }

    private void deleteNpc(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(this.npc.deleteNpc()){

            player.sendMessage("Success to delete npc.");
        } else {
            player.sendMessage("Failed to delete npc.");
        }

        player.closeInventory();
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
        if(item == null) { return; }

        switch (item.getType()){
            case WOODEN_PICKAXE:
                this.npc.onNpManagementEvent(event);
                break;
            case COBWEB:
                this.deleteNpc(event);
                break;
            case NAME_TAG:
                PromptUtils.copyToClipboard((Player) event.getWhoClicked(), this.npc.getUuid());
                break;
        }
    }
}
