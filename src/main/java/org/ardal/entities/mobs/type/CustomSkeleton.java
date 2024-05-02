package org.ardal.entities.mobs.type;

import org.ardal.entities.mobs.CustomMob;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CustomSkeleton extends CustomMob {
    public CustomSkeleton(Location entityLocation) {
        super(EntityType.SKELETON, entityLocation, 50, 1);
    }

    @Override
    public List<ItemStack> getItemsReward() {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.GOLDEN_APPLE, 1));
        items.add(new ItemStack(Material.GOLD_INGOT, 3));
        items.add(new ItemStack(Material.BONE, 2));

        return items;
    }

    @Override
    public void setEntityProperty() {
        Skeleton skeleton = (Skeleton) this.getEntity();
    }
}
