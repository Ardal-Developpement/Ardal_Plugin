package org.ardal.inventories.chunk.modifiers.mob_chunk;

import org.ardal.inventories.CICarousel;
import org.ardal.inventories.CIWithBackBtn;
import org.ardal.objects.chunk.modifiers.ChunkMobModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SpawningMobEditorInventory extends CIWithBackBtn {
    public static final String PAGE_NAME = "Spawning mob";

    private final ChunkMobModifier mobModifier;

    public SpawningMobEditorInventory(ChunkMobModifier mobModifier, Player player) {
        super("Spawning mobs", 36, player, MobModifierEditorInventory.PAGE_NAME);
        this.mobModifier = mobModifier;
    }
    

    @Override
    public void onItemsClick(InventoryClickEvent event) {

    }

    @Override
    public void onBackBtnClick(InventoryClickEvent event) {

    }
}
