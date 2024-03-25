package org.ardal.inventories.npc;

/*

public class NpcManagementInventory extends CustomInventory implements CellCallBack {

    private final CustomNPCObj npc;
    public NpcManagementInventory(CustomNPCObj npc, Player player){
        super(npc.getNpcName() + " management:", 27, player);
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

    private void deleteNpc(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(Ardal.getInstance().getManager(CustomNPCManager.class).deleteNpc(this.npc.getId())){
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
                this.npc.onNpcManagmentClickEvent(event);
                break;
            case COBWEB:
                this.deleteNpc(event);
                break;
        }
    }


}

   */
