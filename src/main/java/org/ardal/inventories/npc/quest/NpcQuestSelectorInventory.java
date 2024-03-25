package org.ardal.inventories.npc.quest;

import org.ardal.Ardal;
import org.ardal.api.inventories.CICell;
import org.ardal.api.inventories.CustomInventory;
import org.ardal.api.inventories.callback.CellCallBack;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.managers.QuestManager;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.objects.PlayerObj;
import org.ardal.objects.QuestObj;
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
        super(npc.getName(), UI_SIZE, player);

        this.questNpc = npc;
        this.nbRandomQuest = nbRandomQuest;

        int i = 0;
        for(QuestObj questObj : this.getRandomQuest()){
            this.setCell(new CICell(questObj.getQuestBook(),
                    i++,
                    null,
                    null,
                    this,
                    null
            ));
        }
    }

    @Override
    public void onCIClick(InventoryClickEvent event){
        event.setCancelled(true);
        CICell cell = this.getCell(event.getSlot());
        if(cell != null){
            cell.onCellClick(event);
        }
    }

    private List<QuestObj> getRandomQuest(){
        List<QuestObj> rdQuest = new ArrayList<>();
        List<QuestObj> allQuestObjs = this.questNpc.getAllActiveQuestIdWithCoef();
        Random random = new Random();
        for(int i = 0; i < this.nbRandomQuest; i++){
            if(allQuestObjs.isEmpty()){
                break;
            }
            QuestObj questObj = allQuestObjs.get(random.nextInt(allQuestObjs.size()));
            allQuestObjs.remove(questObj);
            rdQuest.add(questObj);
        }

        return rdQuest;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if(item == null) { return; }

        Player player = (Player) event.getWhoClicked();
        String questName = item.getItemMeta().getDisplayName();

        new PlayerObj(player).addPlayerActiveQuest(questName);

        String msg = "You select a new quest: " + questName + "\nGood luck!";
        player.sendMessage(ChatUtils.getFormattedMsg(this.questNpc.getName(), msg));
        PlayerUtils.giveItemStackToPlayer(new QuestObj(questName).getQuestBook(), player);

        this.closeInventory();
    }
}
