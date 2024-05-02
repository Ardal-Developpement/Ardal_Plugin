package org.ardal.entities.mobs.type;

import org.ardal.entities.mobs.CustomMob;
import org.ardal.utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class CustomSkeleton extends CustomMob {

    public CustomSkeleton(Location entityLocation, int mobLevel) {
        super(EntityType.SKELETON, entityLocation, mobLevel, 10 * mobLevel, 1);
    }


    @Override
    public List<ItemStack> getItemsReward() {
        List<ItemStack> items = new ArrayList<>();

        if(this.getMobLevel() <= 10) {
            items.add(new ItemStack(Material.GOLD_NUGGET, MathUtils.getRandom(3, 5)));
            items.add(new ItemStack(Material.BONE, MathUtils.getRandom(0, 4)));
        } else if (this.getMobLevel() <= 20) {
            items.add(new ItemStack(Material.GOLDEN_APPLE, MathUtils.getRandom(0, 1)));
            items.add(new ItemStack(Material.GOLD_NUGGET, MathUtils.getRandom(5, 10)));
            items.add(new ItemStack(Material.BONE, MathUtils.getRandom(0, 4)));
        } else {
            items.add(new ItemStack(Material.GOLDEN_APPLE, MathUtils.getRandom(1, 2)));
            items.add(new ItemStack(Material.GOLD_INGOT, MathUtils.getRandom(1, 3)));
            items.add(new ItemStack(Material.BONE, MathUtils.getRandom(0, 4)));
        }

        return items;
    }

    @Override
    public void setEntityProperty() {
        Skeleton skeleton = (Skeleton) this.getEntity();

        /*
                Mob power
         */

        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, this.getMobLevel(), true, true));


        if(this.getMobLevel() <= 30) {
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, this.getMobLevel() / 10, true, true));
        } else {
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, this.getMobLevel() / 5, true, true));
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, true, true));

        }

        /*
                Mob stuff
         */
    }
}
