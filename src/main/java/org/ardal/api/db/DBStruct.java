package org.ardal.api.db;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.utils.JsonUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class DBStruct {
    private JsonObject db;
    private final String dbFileName;
    private final Path dbFilePath;

    public DBStruct(Path pluginDirPath, String dbFileName){
        this.dbFileName = dbFileName;
        this.dbFilePath = pluginDirPath.resolve(this.dbFileName);
        this.loadDB();
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

    public JsonObject getDb() {
        return db;
    }

    public List<String> getKeySet(){
        return JsonUtils.getKeySet(this.db);
    }
}
