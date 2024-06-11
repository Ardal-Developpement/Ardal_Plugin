package org.ardal.inventories.chunk.modifiers.mob_chunk;

import org.ardal.api.inventories.CICell;
import org.ardal.inventories.CIWithBackBtn;
import org.ardal.inventories.chunk.ChunkEditorInventory;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.ardal.objects.chunk.modifiers.ChunkMobModifier;
import org.ardal.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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
                        null,
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
        return ItemUtils.QuickItemSet(Material.DIAMOND_AXE, "Spawning mob level.");
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
                new SpawningMobEditorInventory(this.mobModifier, this.getPlayer()).showInventory();
                break;
            case DIAMOND_AXE:
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
        new ChunkEditorInventory(this.getPlayer(), this.chunkGroupObj);
    }
}
