package org.ardal.npc;

import org.ardal.Ardal;
import org.ardal.objects.CustomNPCObj;
import org.ardal.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class NpcManagementTool implements Listener {
    private static final String toolName = "Npc Management Tool";
    private static final Material itemMaterial = Material.SPECTRAL_ARROW;
    private final ItemStack tool;
    public NpcManagementTool(){
        this.tool = new ItemStack(itemMaterial);
        ItemMeta meta = this.tool.getItemMeta();

        meta.setDisplayName(toolName);
        meta.setLore(Arrays.asList("Npc management tool."));
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);

        this.tool.setItemMeta(meta);
        Bukkit.getServer().getPluginManager().registerEvents(this, Ardal.getInstance());
    }

    public boolean getToolToPlayer(Player player){
        if(player != null){
            PlayerUtils.giveItemStackToPlayer(this.tool, player);
            return true;
        }

        return false;
    }

    public void getInventory(CustomNPCObj npc, Player player){
        player.openInventory(new NpcManagementInventory(npc, player).getInventory());
    }
}
