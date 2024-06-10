package org.ardal.inventories.chunk.modifiers.mob_chunk;

import org.ardal.inventories.CICarousel;
import org.ardal.objects.chunk.modifiers.ChunkMobModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class SpawningMobEditorInventory extends CICarousel {
    private final ChunkMobModifier mobModifier;

    public SpawningMobEditorInventory(ChunkMobModifier mobModifier, Player player) {
        super("Spawning mobs", 36, player);

        this.mobModifier = mobModifier;


    }
    

    @Override
    public void onItemsClick(InventoryClickEvent event) {

    }
}
