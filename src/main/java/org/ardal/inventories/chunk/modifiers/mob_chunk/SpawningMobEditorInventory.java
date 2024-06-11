package org.ardal.inventories.chunk.modifiers.mob_chunk;

import org.ardal.api.entities.mobs.MobType;
import org.ardal.api.inventories.CICell;
import org.ardal.inventories.CIWithBackBtn;
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

public class SpawningMobEditorInventory extends CIWithBackBtn {
    public static final String PAGE_NAME = "Spawning mob";

    private final ChunkGroupObj chunkGroupObj;
    private final ChunkMobModifier mobModifier;

    public SpawningMobEditorInventory(ChunkGroupObj chunkGroupObj, ChunkMobModifier mobModifier, Player player) {
        super("Spawning mobs", 36, player, MobModifierEditorInventory.PAGE_NAME);
        this.chunkGroupObj = chunkGroupObj;
        this.mobModifier = mobModifier;

        this.build(
                this.getItems(),
                new CICell(null,
                        -1,
                        null,
                        null,
                        this,
                        null
                )
                );
    }

    private List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(this.getCustomSkeletonItem());

        return items;
    }

    private ItemStack getCustomSkeletonItem() {
        return ItemUtils.QuickItemSet(Material.SKELETON_SPAWN_EGG, "Skeleton");
    }

    @Override
    public void onItemsClick(InventoryClickEvent event) {
        Material material = event.getCurrentItem().getType();
        switch (material) {
            case SKELETON_SPAWN_EGG:
                this.switchMobSpawn(MobType.SKELETON, event);
        }
    }

    private void switchMobSpawn(MobType mobType, InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        ItemMeta meta = item.getItemMeta();

        if(meta.getEnchants().isEmpty()) {
            // Currently off, turning on mob spawning
            meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
            this.mobModifier.addSpawningMob(mobType);

        } else {
            // Currently on, turning off mob spawning
            meta.removeEnchant(Enchantment.VANISHING_CURSE);
            this.mobModifier.removeSpawningMob(mobType);
        }

        item.setItemMeta(meta);
        this.getCell(event.getSlot()).setItem(item);
    }

    @Override
    public void onBackBtnClick(InventoryClickEvent event) {
        new MobModifierEditorInventory(this.chunkGroupObj, this.getPlayer()).showInventory();
    }
}
