package org.ardal.inventories.chunk.modifiers.mob_chunk;

import org.ardal.api.inventories.CICell;
import org.ardal.inventories.CIWithBackBtn;
import org.ardal.inventories.chunk.ChunkEditorInventory;
import org.ardal.models.MChunkMob;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.ardal.objects.chunk.modifiers.ChunkMobModifier;
import org.ardal.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
        ItemStack item = ItemUtils.QuickItemSet(Material.CLOCK, "Spawning cooldown");
        this.setCooldown(item);
        return item;
    }

    private ItemStack getEnableItem() {
        ItemStack item = ItemUtils.QuickItemSet(Material.REDSTONE_TORCH, "Enable Spawning");
        this.setSpawningMobEnable(item);
        return item;
    }

    @Override
    public void onItemsClick(InventoryClickEvent event) {
        switch (event.getCurrentItem().getType()) {
            case CREEPER_SPAWN_EGG:
                this.closeInventory();
                new SpawningMobEditorInventory(this.chunkGroupObj, this.mobModifier, this.getPlayer()).showInventory();
                break;
            case DIAMOND_AXE:
                this.levelProcess(event);
                break;
            case CLOCK:
                this.cooldownProcess(event);
                break;
            case REDSTONE_TORCH:
                this.spawningMobEnable(event);
                break;
            default:
                System.err.println("Unknown item type: " + event.getCurrentItem().getType());
        }
    }

    @Override
    public void onBackBtnClick(InventoryClickEvent event) {
        new ChunkEditorInventory(this.getPlayer(), this.chunkGroupObj).showInventory();
    }

    private void levelProcess(InventoryClickEvent event) {
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
    }

    private void cooldownProcess(InventoryClickEvent event) {
        float nbCooldown = 5f;
        if(event.isLeftClick()) {
            // Add level
            this.mobModifier.getMChunkMob().addCooldown(nbCooldown);
            this.mobModifier.getMChunkMob().updateChunkMob();
            this.setCooldown(event.getCurrentItem());
        } else if(event.isRightClick()){
            // Remove level
            if(this.mobModifier.getMChunkMob().getCooldown() - nbCooldown  < 0){
                return;
            }

            this.mobModifier.getMChunkMob().addCooldown(-nbCooldown);
            this.mobModifier.getMChunkMob().updateChunkMob();
            this.setCooldown(event.getCurrentItem());
        }
    }

    private void spawningMobEnable(InventoryClickEvent event) {
        boolean currentState = this.mobModifier.getMChunkMob().isEnable();
        this.mobModifier.getMChunkMob().setEnable(!currentState);
        this.mobModifier.getMChunkMob().updateChunkMob();

        this.setSpawningMobEnable(event.getCurrentItem());
    }

    private void setMobLevel(ItemStack spawningMobLevelItem) {
        ItemMeta meta = spawningMobLevelItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("Mob level: " + this.mobModifier.getMChunkMob().getLevel());

        meta.setLore(lore);
        spawningMobLevelItem.setItemMeta(meta);
    }

    private void setCooldown(ItemStack cooldownItem) {
        ItemMeta meta = cooldownItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("Cooldown: " + this.mobModifier.getMChunkMob().getCooldown() + "sec");

        meta.setLore(lore);
        cooldownItem.setItemMeta(meta);
    }

    private void setSpawningMobEnable(ItemStack enabledItem) {
        ItemMeta meta = enabledItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        boolean status = this.mobModifier.getMChunkMob().isEnable();
        lore.add("is enable: " + status);

        if (status) {
            meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        } else {
            meta.removeEnchant(Enchantment.VANISHING_CURSE);
        }

        meta.setLore(lore);
        enabledItem.setItemMeta(meta);
    }

}
