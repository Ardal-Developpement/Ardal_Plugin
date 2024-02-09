package org.ardal.db;

import org.ardal.api.db.JsonDBStruct;
import org.ardal.npc.CustomNPC;
import org.ardal.utils.JsonUtils;
import org.ardal.utils.PromptUtils;
import org.bukkit.command.CommandSender;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NpcDB extends JsonDBStruct {
    private static final String DB_FILE_NAME = "NpcDB.json";
    public NpcDB(Path pluginDirPath) {
        super(pluginDirPath, DB_FILE_NAME);
    }

    public void saveNPC(CustomNPC npc, CommandSender sender){
        this.getDb().add(npc.getNpcUUID().toString(), npc.toJson());
        PromptUtils.sendMsgAndLog(sender, "Success to add: " + npc.getNpcName());
    }

    public List<CustomNPC> loadNPCs(){
        List<CustomNPC> customNPCs = new ArrayList<>();

        for(String npcUUID : JsonUtils.getKeySet(this.getDb())){
            customNPCs.add(new CustomNPC(this.getDb().getAsJsonObject(npcUUID)));
        }

        return customNPCs;
    }
}
