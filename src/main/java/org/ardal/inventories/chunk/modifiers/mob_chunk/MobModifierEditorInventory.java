package org.ardal.inventories.chunk.modifiers.mob_chunk;

import org.ardal.api.inventories.CICell;
import org.ardal.inventories.CIWithBackBtn;
import org.ardal.inventories.chunk.ChunkEditorInventory;
import org.ardal.models.MChunkMob;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.ardal.objects.chunk.modifiers.ChunkMobModifier;
import org.ardal.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MobModifierEditorInventory extends CIWithBackBtn {
    public static final String PAGE_NAME = "Mob modifier editor";

    private final ChunkGroupObj chunkGroupObj;
    private final ChunkMobModifier mobModifier;

    public MobModifierEditorInventory(ChunkGroupObj chunkGroupObj, Player player) {
        super("Mob modifier editor", 36, player, ChunkEditorInventory.PAGE_NAME);
        this.chunkGroupObj = chunkGroupObj;
        this.mobModifier = this.chunkGroupObj.getModifier(ChunkMobModifier.class);

        this.build(
                this.getItems(),
                new CICell(null,
                        -1,
                        this,
                        null,
                        this,
                        null
                )
        );
    }

    private List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(this.getMobTypeItem());
        items.add(this.getSpawningMobLevelItem());
        items.add(this.getCooldownItem());
        items.add(this.getEnableItem());

        return items;
    }

    private ItemStack getMobTypeItem() {
        return ItemUtils.QuickItemSet(Material.CREEPER_SPAWN_EGG, SpawningMobEditorInventory.PAGE_NAME);
    }

    private ItemStack getSpawningMobLevelItem() {
        ItemStack item = ItemUtils.QuickItemSet(Material.DIAMOND_AXE, "Spawning mob level.");
        this.setMobLevel(item);
        return item;
    }

    private ItemStack getCooldownItem() {
        return ItemUtils.QuickItemSet(Material.CLOCK, "Spawning cooldown");
    }

    private ItemStack getEnableItem() {
        return ItemUtils.QuickItemSet(Material.REDSTONE_TORCH, "Enable Spawning");
    }

    @Override
    public void onItemsClick(InventoryClickEvent event) {
        switch (event.getCurrentItem().getType()) {
            case CREEPER_SPAWN_EGG:
                this.closeInventory();
                new SpawningMobEditorInventory(this.chunkGroupObj, this.mobModifier, this.getPlayer()).showInventory();
                break;
            case DIAMOND_AXE:
                int nbLevel = 3;
                if(event.isLeftClick()) {
                    // Add level
                    this.mobModifier.getMChunkMob().addLevel(nbLevel);
                    this.mobModifier.getMChunkMob().updateChunkMob();
                    this.setMobLevel(event.getCurrentItem());
                } else if(event.isRightClick()){
                    // Remove level
                    if(this.mobModifier.getMChunkMob().getLevel() - nbLevel  < 1){
                        return;
                    }

                    this.mobModifier.getMChunkMob().addLevel(-nbLevel);
                    this.mobModifier.getMChunkMob().updateChunkMob();
                    this.setMobLevel(event.getCurrentItem());
                }
                break;
            case CLOCK:
                break;
                case REDSTONE_TORCH:
                    break;
            default:
                System.err.println("Unknown item type: " + event.getCurrentItem().getType());
        }
    }

    @Override
    public void onBackBtnClick(InventoryClickEvent event) {
        new ChunkEditorInventory(this.getPlayer(), this.chunkGroupObj).showInventory();
    }

    private void setMobLevel(ItemStack spawningMobLevelItem) {
        ItemMeta meta = spawningMobLevelItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("Mob level: " + this.mobModifier.getMChunkMob().getLevel());

        meta.setLore(lore);
        spawningMobLevelItem.setItemMeta(meta);
    }
}
