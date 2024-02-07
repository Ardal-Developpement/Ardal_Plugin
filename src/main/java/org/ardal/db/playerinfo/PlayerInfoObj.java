package org.ardal.db.playerinfo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;


public class PlayerInfoObj {
    private final String playerName;
    private final String playerUUID;
    private long adventureLevel;
    private List<UUID> activeQuest;
    private List<UUID> finishedQuest;

    public PlayerInfoObj(Player player){
        this.playerName = player.getName();
        this.playerUUID = player.getUniqueId().toString();
        this.adventureLevel = 0;
    }

    public PlayerInfoObj(JsonObject playerInfoObj){
        JsonElement playerNameObj = playerInfoObj.get("playerName");
        JsonElement playerUUIDObj = playerInfoObj.get("playerUUID");
        JsonElement adventureLevelObj = playerInfoObj.get("adventureLevel");

        if(playerNameObj == null
            || playerUUIDObj == null
            || adventureLevelObj == null)
        {
           throw new IllegalArgumentException("Invalid player info object.");
        }

        this.playerName = playerNameObj.getAsString();
        this.playerUUID = playerUUIDObj.getAsString();
        this.adventureLevel = adventureLevelObj.getAsLong();
    }

    public JsonObject toJson(){
        JsonObject playerInfoObj = new JsonObject();
        playerInfoObj.addProperty("playerName", this.playerName);
        playerInfoObj.addProperty("playerUUID", this.playerUUID);
        playerInfoObj.addProperty("adventureLevel", this.adventureLevel);

        return playerInfoObj;
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

    public List<UUID> getActiveQuest() {
        return activeQuest;
    }

    public List<UUID> getFinishedQuest() {
        return finishedQuest;
    }

    public void addAdventureLevel(long level){
        if(this.adventureLevel + level <= 0){
            this.adventureLevel = 0;
        } else {
            this.adventureLevel += level;
        }
    }

    public boolean addActiveQuest(UUID questUUID){
        // TODO Check if the quest exist

        if(!this.activeQuest.contains(questUUID)) {
            this.activeQuest.add(questUUID);
        }

        this.removeFinishedQuest(questUUID);
        return true;
    }

    public boolean addFinishedQuest(UUID questUUID){
        // TODO Check if the quest exist

        if(!this.finishedQuest.contains(questUUID)) {
            this.finishedQuest.add(questUUID);
        }

        this.removeActiveQuest(questUUID);
        return true;
    }

    public boolean removeActiveQuest(UUID questUUID){
        return this.activeQuest.remove(questUUID);
    }

    public boolean removeFinishedQuest(UUID questUUID){
        return this.finishedQuest.remove(questUUID);
    }

}
