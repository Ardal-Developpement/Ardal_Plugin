package org.ardal.db;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.utils.JsonUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class QuestDB {
    private static final String DB_FILE_NAME = "quest_db.json";
    private JsonObject db;
    private final Path dbFilePath;
    public QuestDB(Path pluginDirPath){
        this.dbFilePath = pluginDirPath.resolve(DB_FILE_NAME);
        this.loadDB();
    }

    public Boolean removeQuestBook(String questBookName){
        if(this.db.getAsJsonObject(questBookName) == null) { return false; }

        this.db.remove(questBookName);
        this.saveDB();

        return true;
    }

    public List<String> getAllQuestName() {
        return JsonUtils.getKeySet(this.db);
    }


    public void loadDB(){
        JsonUtils.createDirIfNotExist(this.dbFilePath.getParent());

        if(!Files.exists(this.dbFilePath)){
            Ardal.writeToLogger("save db at: " + this.dbFilePath);
            JsonUtils.saveJsonAt(new JsonObject(), this.dbFilePath);
        }

        this.db = JsonUtils.loadJsonFromPath(this.dbFilePath);
    }

    public void saveDB(){
        JsonUtils.saveJsonAt(this.db, this.dbFilePath);
    }

    public JsonObject getQuest(String questName){
        return this.db.getAsJsonObject(questName);
    }

    public JsonObject getDb() {
        return db;
    }
}
