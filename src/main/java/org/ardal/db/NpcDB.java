package org.ardal.db;

import com.google.gson.JsonObject;
import org.ardal.api.db.JsonDBStruct;
import org.ardal.api.npc.CustomNpcType;
import org.ardal.npc.quest.QuestNpc;
import org.ardal.objects.CustomNPCObj;
import org.ardal.utils.JsonUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NpcDB extends JsonDBStruct {
    private static final String DB_FILE_NAME = "NpcDB.json";
    public NpcDB(Path pluginDirPath) {
        super(pluginDirPath, DB_FILE_NAME);
    }

    public void saveNPC(CustomNPCObj npc){
        JsonObject npcWrapper = new JsonObject();
        npcWrapper.addProperty("type", npc.getNpcType().toString());
        npcWrapper.add("data", npc.toJson());
        this.getDb().add(npc.getNpcName(), npcWrapper);

        this.saveDB();
    }

    public void removeNpc(String name){
        this.getDb().remove(name);
        this.saveDB();
    }

    public List<CustomNPCObj> loadNPCs(){
        List<CustomNPCObj> customNPCObjs = new ArrayList<>();

        for(String npcName : JsonUtils.getKeySet(this.getDb())){
            JsonObject npcWrapper = this.getDb().get(npcName).getAsJsonObject();

            CustomNPCObj customNPCObj;
            if (npcWrapper.getAsJsonObject("type").getAsString().equals(CustomNpcType.QUEST_NPC.toString())) {
                customNPCObj = new QuestNpc(npcWrapper.getAsJsonObject("data"));
            } else {
                throw new IllegalArgumentException("Unknown npc type");
            }

            customNPCObjs.add(customNPCObj);
        }

        return customNPCObjs;
    }
}
