package org.ardal.npc.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.inventories.npc.quest.NpcMenuSelectorInventory;
import org.ardal.inventories.npc.quest.NpcQuestSelectorInventory;
import org.ardal.inventories.npc.quest.management.QuestIsActiveSelectorInventory;
import org.ardal.managers.CustomNPCManager;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.managers.QuestManager;
import org.ardal.objects.CustomNPCObj;
import org.ardal.objects.QuestObj;
import org.ardal.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestNpc extends CustomNPCObj {
    public List<QuestNpcInfo> questInfoList;

    public QuestNpc(String npcName, Location location){
        super(npcName, location);
        this.questInfoList = new ArrayList<>();
    }

    public QuestNpc(JsonObject npcObj, UUID id){
        super(npcObj, id);
        this.questInfoList = new ArrayList<>();
        JsonElement jsonElement = npcObj.get("additionalProperties");

        if(jsonElement == null) {
            throw new RuntimeException("Invalid npc quest obj.");
        }

        for(JsonElement elem : jsonElement.getAsJsonArray()){
            this.questInfoList.add(new QuestNpcInfo(elem.getAsJsonObject()));
        }
    }

    public void saveNpcAdditionnalInfo(){
        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
         customNPCManager.getNpcDB().getDb().get(this.getId().toString()).getAsJsonObject().add("additionalProperties", this.additionalProperties());
         customNPCManager.getNpcDB().saveDB();
    }

    @NotNull
    public QuestNpcInfo getQuestNpcByObj(QuestObj questObj){
        for(QuestNpcInfo questNpcInfo : this.questInfoList){
            if(questNpcInfo.getQuestName().equals(questObj.getQuestName())){
                return questNpcInfo;
            }
        }

        QuestNpcInfo questNpcInfo = new QuestNpcInfo(questObj.getQuestName(), 1, false);
        this.questInfoList.add(questNpcInfo);
        return questNpcInfo;
    }

    @NotNull
    public QuestNpcInfo getQuestNpcByName(String questName){
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        return this.getQuestNpcByObj(questManager.getQuestObj(questName));
    }

    @Override
    public void onNpcManagmentClickEvent(InventoryClickEvent event) {
        new QuestIsActiveSelectorInventory(this, (Player) event.getWhoClicked()).showInventory();
    }

    @Override
    public JsonArray additionalProperties() {
        JsonArray jsonArray = new JsonArray();

        for(QuestNpcInfo obj : this.questInfoList){
            jsonArray.add(obj.toJson());
        }

        return jsonArray;
    }

    @Override
    public CustomNpcType getNpcType() {
        return CustomNpcType.QUEST_NPC;
    }

    @Override
    public void onNPCInteract(PlayerInteractEntityEvent event) {
        if(!hasOneQuestShowed()){
            event.getPlayer().sendMessage(ChatUtils.getFormattedMsg(this.getNpcName(),
                    String.format("Hey %s, I'm not working today, come back later.",
                            event.getPlayer().getDisplayName())));

            return;
        }

        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);
        int questCooldown = playerInfoManager.getQuestCooldown(event.getPlayer());
        if(questCooldown != 0){
            event.getPlayer().sendMessage(ChatUtils.getFormattedMsg(this.getNpcName(),
                    String.format("Hey %s, I'm busy, please come back in %d minutes.",
                            event.getPlayer().getDisplayName(), questCooldown)));

            return;
        }

        String questName = this.playerHasNpcActiveQuest(event.getPlayer());
        if(questName != null){
            new NpcMenuSelectorInventory(this, event.getPlayer(), questName).showInventory();
        } else {
            new NpcQuestSelectorInventory(this, event.getPlayer(), 9).showInventory();
        }
    }

    /**
     * @return questName if player don't have an active quest that the npc have
     */
    private String playerHasNpcActiveQuest(Player player){
        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);
        List<String> playerQuestList = playerInfoManager.getPlayerActiveQuests(player);

        for(String questName : this.getNpcActiveQuest()){
            if(playerQuestList.contains(questName)){
                return questName;
            }
        }

        return null;
    }

    @Override
    public void onNpcManageToolInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        customNPCManager.getNpcManagementTool().openNpcManagementInventory(this, player);
    }

    public List<String> getNpcActiveQuest(){
        List<String> list = new ArrayList<>();
        for(QuestNpcInfo questNpcInfo : this.questInfoList){
            if(questNpcInfo.getIsShow() && !questNpcInfo.isQuestDelete()){
                list.add(questNpcInfo.getQuestName());
            }
        }

        return list;
    }

    public List<String> getAllActiveQuestWithCoef(){
        List<String> allQuest = new ArrayList<>();
        for(QuestNpcInfo questNpcInfo : this.questInfoList){
            for(int i = 0; i < questNpcInfo.getQuestCoef(); i++){
                if(questNpcInfo.getIsShow() && !questNpcInfo.isQuestDelete()){
                    allQuest.add(questNpcInfo.getQuestName());
                }
            }
        }

        return allQuest;
    }

    private boolean hasOneQuestShowed() {
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        if(questManager.getAllQuestNames().isEmpty()){
            return false;
        }

        for(QuestNpcInfo npcInfo : this.questInfoList){
            if(npcInfo.getIsShow() && !npcInfo.isQuestDelete()){
                return true;
            }
        }

        return false;
    }

    public static void addSynopsisToQuestBook(@NotNull ItemStack book, String synopsis){
        ItemMeta meta = book.getItemMeta();

        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        String[] lines = synopsis.split("\n");

        for(int i = lines.length - 1; i >= 0; i--){
            lore.add(0, lines[i]);
        }

        meta.setLore(lore);
        book.setItemMeta(meta);
    }
}