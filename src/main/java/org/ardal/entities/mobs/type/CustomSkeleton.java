package org.ardal.entities.mobs.type;

import org.ardal.entities.mobs.CustomMob;
import org.ardal.utils.MathUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomSkeleton extends CustomMob {

    public CustomSkeleton(Location entityLocation, int mobLevel) {
        super(EntityType.SKELETON, entityLocation, mobLevel, 10 * mobLevel);
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
        skeleton.setCustomName("Level: " + ChatColor.AQUA + this.getMobLevel());
        skeleton.setCustomNameVisible(true);

        /*
                Mob power
         */

        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, this.getMobLevel(), true, false));


        if(this.getMobLevel() <= 30) {
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, this.getMobLevel() / 10, true, false));
        } else {
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, this.getMobLevel() / 5, true, false));
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1, true, false));

        }

        /*
                Mob stuff
         */

        List<ItemStack> possibleHelmet = new ArrayList<>();
        List<ItemStack> possibleChestPlate = new ArrayList<>();
        List<ItemStack> possibleLeggings = new ArrayList<>();
        List<ItemStack> possibleBoots = new ArrayList<>();

        List<Enchantment> possibleEnchantments = new ArrayList<>();

        if (this.getMobLevel() <= 3) {
            possibleHelmet.add(new ItemStack(Material.LEATHER_HELMET));
            possibleChestPlate.add(new ItemStack(Material.LEATHER_CHESTPLATE));
            possibleLeggings.add(new ItemStack(Material.LEATHER_LEGGINGS));
            possibleBoots.add(new ItemStack(Material.LEATHER_BOOTS));
        } else if (this.getMobLevel() <= 10) {
            possibleHelmet.add(new ItemStack(Material.IRON_HELMET));
            possibleChestPlate.add(new ItemStack(Material.IRON_CHESTPLATE));
            possibleLeggings.add(new ItemStack(Material.IRON_LEGGINGS));
            possibleBoots.add(new ItemStack(Material.IRON_BOOTS));

            possibleEnchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            possibleEnchantments.add(Enchantment.PROTECTION_FIRE);
        } else if (this.getMobLevel() <= 20){
            possibleHelmet.add(new ItemStack(Material.GOLDEN_HELMET));
            possibleChestPlate.add(new ItemStack(Material.GOLDEN_CHESTPLATE));
            possibleLeggings.add(new ItemStack(Material.GOLDEN_LEGGINGS));
            possibleBoots.add(new ItemStack(Material.GOLDEN_BOOTS));

            possibleEnchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
            possibleEnchantments.add(Enchantment.PROTECTION_FIRE);
            possibleEnchantments.add(Enchantment.THORNS);
        }

        ItemStack helmet = getRandomItem(possibleHelmet, possibleEnchantments, 0.6);
        ItemStack chestplate = getRandomItem(possibleChestPlate, possibleEnchantments, 0.3);
        ItemStack leggings = getRandomItem(possibleLeggings, possibleEnchantments, 0.4);
        ItemStack boots = getRandomItem(possibleBoots, possibleEnchantments, 0.6);

        if (helmet != null) {
            skeleton.getEquipment().setHelmet(helmet);
        }

        if (chestplate != null) {
            skeleton.getEquipment().setChestplate(chestplate);
        }

        if (leggings != null) {
            skeleton.getEquipment().setLeggings(leggings);
        }

        if (boots != null) {
            skeleton.getEquipment().setBoots(boots);
        }
    }

    @Nullable
    private ItemStack getRandomItem(List<ItemStack> items, List<Enchantment> enchantments, double probability) {
        if(MathUtils.getRandom(0, 100) < probability * 100) {
            return setRandomEnchant(items.get(MathUtils.getRandom(0, items.size())), enchantments);
        }

        return null;
    }

    private ItemStack setRandomEnchant(ItemStack item, List<Enchantment> enchantments) {
        for(Enchantment enchantment : enchantments){
            item.addUnsafeEnchantment(enchantment, MathUtils.getRandom(1, enchantment.getMaxLevel()));
        }

        return item;
    }
}
