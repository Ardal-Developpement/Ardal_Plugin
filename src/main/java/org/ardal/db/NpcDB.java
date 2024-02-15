package org.ardal.db;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.api.db.JsonDBStruct;
import org.ardal.managers.CustomNPCManager;
import org.ardal.objects.CustomNPCObj;
import org.ardal.utils.JsonUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NpcDB extends JsonDBStruct {
    private static final String DB_FILE_NAME = "NpcDB.json";
    public NpcDB(Path pluginDirPath) {
        super(pluginDirPath, DB_FILE_NAME);
    }

    public void saveNPC(CustomNPCObj npc){
        JsonObject npcObj = npc.toJson();
        npcObj.addProperty("type", npc.getNpcType().toString());
        this.getDb().add(npc.getId().toString(), npcObj);

        this.saveDB();
    }

    public void removeNpc(UUID id){
        this.getDb().remove(id.toString());
        this.saveDB();
    }

    public List<CustomNPCObj> loadNPCs() {
        List<CustomNPCObj> customNPCObjs = new ArrayList<>();

        for(String npcId : JsonUtils.getKeySet(this.getDb())){
            CustomNPCManager customNPCManager = Ardal.getInstance().getManager(CustomNPCManager.class);
            customNPCManager.invokeNpc(UUID.fromString(npcId));
        }

        return customNPCObjs;
    }
}
