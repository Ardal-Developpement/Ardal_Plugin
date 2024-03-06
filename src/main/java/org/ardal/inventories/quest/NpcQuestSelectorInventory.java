package org.ardal.inventories.quest;

import org.ardal.Ardal;
import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.managers.QuestManager;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.utils.ChatUtils;
import org.ardal.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NpcQuestSelectorInventory extends CustomInventory implements CellCallBack {
    private static final int UI_SIZE = 27;
    private final QuestNpc questNpc;
    private final int nbRandomQuest;
    public NpcQuestSelectorInventory(QuestNpc npc, Player player, int nbRandomQuest) {
        super(npc.getNpcName(), UI_SIZE, player);

        this.questNpc = npc;
        this.nbRandomQuest = nbRandomQuest;

        int i = 0;
        for(String questName : this.getRandomQuest()){
            this.setCell(new CICell(this.getFormattedQuestBook(questName),
                    i++,
                    null,
                    null,
                    this,
                    null
            ));
        }
    }

    private ItemStack getFormattedQuestBook(String questName){
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        ItemStack book = questManager.getQuestBook(questName);
        QuestNpc.addSynopsisToQuestBook(book, questManager.getQuestSynopsis(questName));

        return book;
    }

    @Override
    public void onCIClick(InventoryClickEvent event){
        event.setCancelled(true);
        CICell cell = this.getCell(event.getSlot());
        if(cell != null){
            cell.onCellClick(event);
        }

    }

    /**
     * @return questName if player don't have an active quest that the npc have
     */
    public String playerHasNpcActiveQuest(){
        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);
        List<String> playerQuestList = playerInfoManager.getPlayerActiveQuests(this.getPlayer());

        for(String questName : this.questNpc.getNpcActiveQuest()){
            if(playerQuestList.contains(questName)){
                return questName;
            }
        }

        return null;
    }

    private List<String> getRandomQuest(){
        List<String> rdQuest = new ArrayList<>();
        List<String> allQuest = this.questNpc.getAllActiveQuestWithCoef();
        Random random = new Random();
        for(int i = 0; i < this.nbRandomQuest; i++){
            if(allQuest.isEmpty()){
                break;
            }
            String questName = allQuest.get(random.nextInt(allQuest.size()));
            allQuest.remove(questName);
            rdQuest.add(questName);
        }

        return rdQuest;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item == null) { return; }

        Player player = (Player) event.getWhoClicked();
        String questName = item.getItemMeta().getDisplayName();

        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        playerInfoManager.addPlayerActiveQuest(player, questName);

        String msg = "You select a new quest: " + questName + "\nGood luck!";
        player.sendMessage(ChatUtils.getFormattedMsg(this.questNpc.getNpcName(), msg));
        PlayerUtils.giveItemStackToPlayer(questManager.getQuestBook(questName), player);

        this.closeInventory();
    }
}
