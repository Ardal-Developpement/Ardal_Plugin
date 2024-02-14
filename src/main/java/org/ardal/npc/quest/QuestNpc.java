package org.ardal.npc.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.objects.CustomNPCObj;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class QuestNpc extends CustomNPCObj {
    public List<QuestNpcInfo> questInfoList;

    public QuestNpc(String npcName, Location location){
        super(npcName, location);
        this.questInfoList = new ArrayList<>();
    }

    public QuestNpc(JsonObject npcObj){
        super(npcObj);
        this.questInfoList = new ArrayList<>();
        JsonElement jsonElement = npcObj.get("additionalProperties");

        if(jsonElement == null) {
            throw new RuntimeException("Invalid npc quest obj.");
        }

        for(JsonElement elem : jsonElement.getAsJsonArray()){
            this.questInfoList.add(new QuestNpcInfo(elem.getAsJsonObject()));
        }
    }


    @Override
    public JsonElement additionalProperties() {
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
        event.getPlayer().sendMessage("Opening quest npc.");
    }
}
