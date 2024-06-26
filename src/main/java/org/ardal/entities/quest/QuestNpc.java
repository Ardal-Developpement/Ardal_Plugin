package org.ardal.entities.quest;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.ardal.Ardal;
import org.ardal.api.entities.npc.NpcType;
import org.ardal.api.entities.npc.type.QuestNpcInfo;
import org.ardal.exceptions.NpcNotFound;
import org.ardal.inventories.npc.quest.NpcMenuSelectorInventory;
import org.ardal.inventories.npc.quest.NpcQuestSelectorInventory;
import org.ardal.inventories.npc.quest.management.QuestIsShowSelectorInventory;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.models.npc.type.MQuestNpc;
import org.ardal.models.npc.type.MQuestNpcInfo;
import org.ardal.objects.NpcObj;
import org.ardal.objects.PlayerObj;
import org.ardal.objects.QuestObj;
import org.ardal.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuestNpc extends NpcObj implements QuestNpcInfo {
    private static final int DEFAULT_NB_QUEST_SHOW = 3;

    private final MQuestNpcInfo mQuestNpcInfo;

    public QuestNpc(String npcName, Location location, NpcType npcType){
        super(npcName, location, npcType);

        this.mQuestNpcInfo = new MQuestNpcInfo(this.getUuid(), DEFAULT_NB_QUEST_SHOW);
        Ardal.getInstance().getDb().gettQuestNpcInfo().createQuestNpcInfo(this.mQuestNpcInfo);
    }

    public QuestNpc(String npcUuid) throws NpcNotFound {
        super(npcUuid);

        this.mQuestNpcInfo = Ardal.getInstance().getDb().gettQuestNpcInfo().getNpcQuestInfoByUuid(this.getUuid());
    }

    private List<MQuestNpc> getQuestNpcList() {
        return Ardal.getInstance().getDb().gettQuestNpc().getAllQuestNpcsByUuid(this.getUuid());
    }

    @Nullable
    public MQuestNpc getQuestNpcByName(String questName) {
        Integer questId = Ardal.getInstance().getDb().gettQuest().getQuestIdByName(questName, false);
        if(questId == null) { return null; }

        for(MQuestNpc mQuestNpc : this.getQuestNpcList()) {
            if(mQuestNpc.getQuestId() == questId) {
                return mQuestNpc;
            }
        }

        return this.addQuestNpc(questId);
    }

    public MQuestNpc addQuestNpc(int questId) {
        MQuestNpc mQuestNpc = new MQuestNpc(this.getUuid(), questId, 1, false);
        Ardal.getInstance().getDb().gettQuestNpc().createQuestNpc(mQuestNpc);

        return mQuestNpc;
    }

    @Override
    public void onNPCInteractEvent(NPCRightClickEvent event) {
        if(!hasOneQuestShowed()){
            event.getClicker().sendMessage(ChatUtils.getFormattedMsg(this.getName(),
                    String.format("Hey %s, I'm not working today, come back later.",
                            event.getClicker().getDisplayName())));

            return;
        }

        PlayerObj playerObj = Ardal.getInstance().getManager(PlayerInfoManager.class).getPlayerObj(event.getClicker());
        int questCooldown = playerObj.getQuestCooldown();
        if(questCooldown != 0){
            event.getClicker().sendMessage(ChatUtils.getFormattedMsg(this.getName(),
                    String.format("Hey %s, I'm busy, please come back in %d minutes.",
                            event.getClicker().getDisplayName(), questCooldown)));

            return;
        }

        String questName = this.playerHasNpcActiveQuest(event.getClicker());
        if(questName != null){
            new NpcMenuSelectorInventory(this, event.getClicker(), questName).showInventory();
        } else {
            new NpcQuestSelectorInventory(this, event.getClicker(), this.getNbQuestShow()).showInventory();
        }
    }

    @Nullable
    private String playerHasNpcActiveQuest(Player player) {
        PlayerObj playerObj = Ardal.getInstance().getManager(PlayerInfoManager.class).getPlayerObj(player);
        List<String> playerActiveQuests = playerObj.getPlayerActiveQuestNames();

        for(MQuestNpc mQuestNpc : this.getQuestNpcList()) {
            if(playerActiveQuests.contains(mQuestNpc.getQuestName())){
                return mQuestNpc.getQuestName();
            }
        }

        return null;
    }

    @Override
    public void onNpManagementEvent(InventoryClickEvent event) {
        new QuestIsShowSelectorInventory(this, (Player) event.getWhoClicked()).showInventory();
    }

    public List<QuestObj> getAllActiveQuestIdWithCoef(){
        List<QuestObj> questObjs = new ArrayList<>();

        for(MQuestNpc mQuestNpc : this.getQuestNpcList()){
            if(mQuestNpc.getIsShow() && mQuestNpc.getQuestIsActive()) {
                for(int i = 0; i < mQuestNpc.getQuestCoef(); i++) {
                    questObjs.add(mQuestNpc.getQuestObj());
                }
            }
        }

        return questObjs;
    }

    private boolean hasOneQuestShowed() {
        for(MQuestNpc mQuestNpc : this.getQuestNpcList()) {
            if(mQuestNpc.getQuestIsActive() && mQuestNpc.getIsShow()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getNbQuestShow() {
        return this.mQuestNpcInfo.getNbQuestShow();
    }

    @Override
    public boolean setNbQuestShow(int nbQuestShow) {
        this.mQuestNpcInfo.setNbQuestShow(nbQuestShow);
        return this.mQuestNpcInfo.updateQuestNpcInfo();
    }
}

