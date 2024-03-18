package org.ardal.objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.managers.PlayerInfoManager;
import org.ardal.managers.QuestManager;
import org.ardal.utils.DateUtils;
import org.ardal.utils.JsonUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PlayerInfoObj {
    private final String playerName;
    private final String playerUUID;
    private long adventureLevel;
    private List<String> activeQuest;
    private List<String> finishedQuest;
    private Date questCooldown;

    public PlayerInfoObj(Player player){
        this.playerName = player.getName();
        this.playerUUID = player.getUniqueId().toString();
        this.activeQuest = new ArrayList<>();
        this.finishedQuest = new ArrayList<>();
        this.adventureLevel = 0;
    }

    public PlayerInfoObj(JsonObject playerInfoObj){
        JsonElement playerNameElem = playerInfoObj.get("playerName");
        JsonElement playerUUIDElem = playerInfoObj.get("playerUUID");
        JsonElement adventureLevelElem = playerInfoObj.get("adventureLevel");
        JsonElement activeQuestElem = playerInfoObj.get("activeQuests");
        JsonElement finishedQuestElem = playerInfoObj.get("finishedQuests");
        JsonElement questCooldownElem = playerInfoObj.get("questCooldown");

        if(playerNameElem == null
            || playerUUIDElem == null
            || adventureLevelElem == null
            || activeQuestElem == null
            || finishedQuestElem == null)
        {
           throw new IllegalArgumentException("Invalid player info object.");
        }

        this.playerName = playerNameElem.getAsString();
        this.playerUUID = playerUUIDElem.getAsString();
        this.adventureLevel = adventureLevelElem.getAsLong();
        this.activeQuest = JsonUtils.jsonArrayToStrList(activeQuestElem);
        this.finishedQuest = JsonUtils.jsonArrayToStrList(finishedQuestElem);

        if(questCooldownElem != null) {
            this.questCooldown = DateUtils.toDate(questCooldownElem.getAsString());
            if(this.questCooldown != null && new Date().after(this.questCooldown)) {
                this.questCooldown = null;
            }
        }
    }

    public JsonObject toJson(){
        JsonObject playerInfoObj = new JsonObject();
        playerInfoObj.addProperty("playerName", this.playerName);
        playerInfoObj.addProperty("playerUUID", this.playerUUID);
        playerInfoObj.addProperty("adventureLevel", this.adventureLevel);
        playerInfoObj.add("activeQuests", JsonUtils.jsonArrayFromStrList(this.activeQuest));
        playerInfoObj.add("finishedQuests", JsonUtils.jsonArrayFromStrList(this.finishedQuest));

        if(this.questCooldown != null && new Date().before(this.questCooldown)){
            playerInfoObj.addProperty("questCooldown", DateUtils.toString(this.questCooldown));
        }

        return playerInfoObj;
    }

    public void savePlayerInfo(){
        PlayerInfoManager playerInfoManager = Ardal.getInstance().getManager(PlayerInfoManager.class);

    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public long getAdventureLevel() {
         return adventureLevel;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<String> getActiveQuest() {
        return activeQuest;
    }

    public List<String> getFinishedQuest() {
        return finishedQuest;
    }

    @Nullable
    public Date getQuestCooldown() {
        if(this.questCooldown != null && new Date().after(this.questCooldown)){
            this.setQuestCooldown(null);
        }

        return this.questCooldown;
    }

    public void addAdventureLevel(long level){
        if(this.adventureLevel + level <= 0){
            this.adventureLevel = 0;
        } else {
            this.adventureLevel += level;
        }
    }

    public boolean addActiveQuest(String questName){
        // TODO Check if the quest exist
        if(!this.activeQuest.contains(questName)) {
            this.activeQuest.add(questName);
        }

        if(!this.removeFinishedQuest(questName)){
            this.savePlayerInfo();
            return false;
        }

        return true;
    }

    public boolean addFinishedQuest(String questName){
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        if(!questManager.questExist(questName)){
            return false;
        }

        if(!this.finishedQuest.contains(questName)) {
            this.finishedQuest.add(questName);
        }

        if(!this.removeActiveQuest(questName)){
            this.savePlayerInfo();
            return false;
        }

        return true;
    }

    public void setQuestCooldown(@Nullable Date cooldown) {
        this.questCooldown = cooldown;
        this.savePlayerInfo();
    }

    public boolean removeActiveQuest(String questString){
        if(this.activeQuest.remove(questString)){
            this.savePlayerInfo();
            return true;
        }

        return false;
    }

    public boolean removeFinishedQuest(String questString){
        if(this.finishedQuest.remove(questString)){
            this.savePlayerInfo();
            return true;
        }

        return false;
    }
}
