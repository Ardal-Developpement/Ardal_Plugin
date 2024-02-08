package org.ardal.db.quest;

import com.google.gson.JsonObject;
import com.google.gson.stream.MalformedJsonException;
import org.ardal.api.db.DBStruct;

import java.nio.file.Path;

public class QuestDB extends DBStruct {
    private static final String DB_FILE_NAME = "QuestDB.json";
    public QuestDB(Path pluginDirPath){
        super(pluginDirPath, DB_FILE_NAME);
    }

    public JsonObject getQuestAsJsonObject(String questName){
        return this.getDb().getAsJsonObject(questName);
    }

    public QuestObj getQuestAsQuestObj(String questName) throws MalformedJsonException {
        return new QuestObj(this.getDb().getAsJsonObject(questName));
    }
}
