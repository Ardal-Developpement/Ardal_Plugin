package org.ardal.objects;

import org.ardal.utils.HashUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class CustomItemObj {
    private final ItemStack item;
    private String hashId;
    private int nbUsage;
    public CustomItemObj(ItemStack item){
        this.item = item.clone();
        this.nbUsage = 1;
    }

    public CustomItemObj(ConfigurationSection section){
        this.nbUsage = section.getInt("nbUsage");
        this.item = section.getItemStack("item");
    }

    public void addItem(ConfigurationSection section){
        ConfigurationSection containerSection = section.getConfigurationSection(this.getHashId());

        if(containerSection != null){
            containerSection.set("nbUsage", containerSection.getInt("nbUsage") + 1);
        } else {
            containerSection = section.createSection(this.getHashId());
            containerSection.set("nbUsage", 1);
            containerSection.set("item", this.item);
        }
    }

    public String getHashId() {
        if(this.hashId == null){
            this.hashId = HashUtils.getSHA1Hash(this.item.serialize().toString());
        }

        return this.hashId;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getNbUsage() {
        return nbUsage;
    }

    public void setNbUsage(int nbUsage) {
        this.nbUsage = nbUsage;
    }
}
