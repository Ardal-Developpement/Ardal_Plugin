package org.ardal.inventories.quest.management;

import org.ardal.inventories.CICarousel;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class QuestIsActiveSelectorInventoryRework extends CICarousel {
    public QuestIsActiveSelectorInventoryRework(String title, int size, Player player, List<ItemStack> items) {
        super(title, size, player, items);
    }

    @Override
    public void onItemsClick(InventoryClickEvent event) {

    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }
}
