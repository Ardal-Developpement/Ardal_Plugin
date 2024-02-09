package org.ardal.db;

import com.google.gson.JsonObject;
import com.google.gson.stream.MalformedJsonException;
import org.ardal.api.db.JsonDBStruct;
import org.ardal.objects.QuestObj;
import org.bukkit.command.CommandSender;

import java.nio.file.Path;

public class QuestDB extends JsonDBStruct {
    private static final String DB_FILE_NAME = "QuestDB.json";
    public QuestDB(Path pluginDirPath){
        super(pluginDirPath, DB_FILE_NAME);
    }

    public JsonObject getQuestAsJsonObject(String questName){
        return this.getDb().getAsJsonObject(questName);
    }

    public QuestObj getQuestAsQuestObj(CommandSender sender, String questName) throws MalformedJsonException {
        return new QuestObj(sender, this.getDb().getAsJsonObject(questName));
    }
}
