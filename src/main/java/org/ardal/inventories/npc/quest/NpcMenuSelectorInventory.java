package org.ardal.inventories.npc.quest;

import org.ardal.Ardal;
import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.managers.QuestManager;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.utils.BukkitUtils;
import org.ardal.utils.ChatUtils;
import org.ardal.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class NpcMenuSelectorInventory extends CustomInventory implements CellCallBack {
    private final QuestNpc questNpc;
    private final String questName;
    public NpcMenuSelectorInventory(QuestNpc questNpc, Player player, String questName) {
        super(questNpc.getNpcName() + ":", 27, player);

        this.questNpc = questNpc;
        this.questName = questName;

        this.setCell(new CICell(
                this.getDepositItem(),
                2, 1,
                null,
                null,
                this,
                null)
        );

        this.setCell(new CICell(
                this.getCancelQuestItem(),
                6, 1,
                null,
                null,
                this,
                null)
        );
    }

    private void onQuestCanceledEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);

        playerInfoManager.removePlayerActiveQuest(player, this.questName);
        player.sendMessage(ChatUtils.getFormattedMsg(this.questNpc.getNpcName(),
        "Hey, I remove your quest: " + this.questName + "."));

        // Add quest cooldown
        playerInfoManager.setQuestCooldown(player, 3);

        this.closeInventory();
    }

    private void onDepositItemsEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(this.isPlayerValidQuest(player, questName)){
            String msg = "Well done!\nYou successfully complete the quest: " + questName + "\nGood job!";
            player.sendMessage(ChatUtils.getFormattedMsg(this.questNpc.getNpcName(), msg));
        } else {
            String msg = "You're missing some quest items, come back to me when you've got them all.";
            player.sendMessage(ChatUtils.getFormattedMsg(this.questNpc.getNpcName(), msg));
        }

        this.closeInventory();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item == null) { return; }

        switch (item.getType()){
            case GRASS_BLOCK:
                this.onDepositItemsEvent(event);
                break;
            case BARRIER:
                this.onQuestCanceledEvent(event);
                break;
        }
    }

    private boolean isPlayerValidQuest(Player player, String questName){
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        List<ItemStack> itemsRequest = questManager.getItemsQuestRequest(questName);
        if(BukkitUtils.itemListContainItems(
                Arrays.asList(player.getInventory().getContents()), itemsRequest))
        {
            PlayerUtils.removeItemsToPlayer(itemsRequest, player);

            PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);
            playerInfoManager.addPlayerFinishedQuest(player, questName);

            this.giveRewardItemToPlayer(questName, player);

            return true;
        }

        return false;
    }

    private void giveRewardItemToPlayer(String questName, Player player) {
        player.sendMessage(ChatUtils.getFormattedMsg(this.questNpc.getNpcName(), " Here's your reward for this quest."));

        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        for (ItemStack item : questManager.getItemQuestReward(questName)){
            PlayerUtils.giveItemStackToPlayer(item, player);
        }
    }

    private ItemStack depositItem = null;

    private ItemStack getDepositItem(){
        if(this.depositItem != null) { return this.depositItem; }
        this.depositItem = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta meta = this.depositItem.getItemMeta();

        meta.setDisplayName("Deposit items");
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);

        this.depositItem.setItemMeta(meta);
        return this.depositItem;
    }

    private ItemStack cancelQuestItem = null;

    private ItemStack getCancelQuestItem(){
        if(this.cancelQuestItem != null) { return this.cancelQuestItem; }

        this.cancelQuestItem = new ItemStack(Material.BARRIER);
        ItemMeta meta = this.cancelQuestItem.getItemMeta();

        meta.setDisplayName("Cancel quest.");
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);

        this.cancelQuestItem.setItemMeta(meta);
        return this.cancelQuestItem;
    }
}
