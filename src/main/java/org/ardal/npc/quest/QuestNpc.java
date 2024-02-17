package org.ardal.npc.quest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.managers.CustomNPCManager;
import org.ardal.objects.CustomNPCObj;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;

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

    @Override
    public void onNpcManageToolInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("Interact with: " + this.getNpcName());

        CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
        customNPCManager.getNpcManagementTool().openNpcManagementInventory(this, player);
    }
}
