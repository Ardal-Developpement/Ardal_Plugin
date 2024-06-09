package org.ardal.inventories.chunk;

import org.ardal.api.chunks.ChunkModifierType;
import org.ardal.api.inventories.CICell;
import org.ardal.inventories.CICarousel;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.ardal.objects.chunk.ChunkModifier;
import org.ardal.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ChunkEditorInventory extends CICarousel {
    private final ChunkGroupObj chunkGroupObj;
    private final List<ChunkModifier> savedModifiers;

    public ChunkEditorInventory(Player player, ChunkGroupObj chunkGroupObj) {
        super("Chunk Editor", 36, player);
        this.chunkGroupObj = chunkGroupObj;
        this.savedModifiers = this.chunkGroupObj.getModifiers();

        this.buildCarousel(this.modifiersAsItems(), new CICell(null,
                -1,
                null,
                null,
                this,
                null
        ));
    }

    private List<ItemStack> modifiersAsItems() {
        List<ItemStack> items = new ArrayList<>();
        List<ChunkModifierType> chunkGroupModifierTypes = this.chunkGroupObj.getModifierTypes();

        for(ChunkModifierType type : ChunkModifierType.values()) {
            ItemStack item = null;
            switch (type) {
                case MOB:
                    item = this.getItemForMobModifier();
                    if (chunkGroupModifierTypes.contains(ChunkModifierType.MOB)) {
                        item = ItemUtils.QuickItemEnchantSet(item, Enchantment.VANISHING_CURSE, 1, false);
                    }

                    break;
            }

            if(item != null) {
                items.add(item);
            }
        }

        return items;
    }

    private ItemStack getItemForMobModifier() {
        return ItemUtils.QuickItemSet(Material.CREEPER_HEAD, "Mob modifier editor");
    }


    @Override
    public void onItemsClick(InventoryClickEvent event) {
        Material material = event.getCurrentItem().getType();
        switch (material) {
            case CREEPER_HEAD:
                event.getWhoClicked().sendMessage("Clicked on Mob chunk editor.");
        }
    }
}
