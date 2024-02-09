package org.ardal.api.db;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.utils.JsonUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class JsonDBStruct extends DBStruct {
    private JsonObject db;

    public JsonDBStruct(Path pluginDirPath, String dbFileName) {
        super(pluginDirPath, dbFileName);
    }

    @Override
    public void loadDB() {
        JsonUtils.createDirIfNotExist(this.getDbFilePath().getParent());

        if(!Files.exists(this.getDbFilePath())){
            Ardal.writeToLogger("save db at: " + this.getDbFilePath());
            JsonUtils.saveJsonAt(new JsonObject(), this.getDbFilePath());
        }

        this.db = JsonUtils.loadJsonFromPath(this.getDbFilePath());
    }

    @Override
    public void saveDB(){
        JsonUtils.saveJsonAt(this.db, this.getDbFilePath());
    }

    @Override
    public List<String> getKeySet(){
        return JsonUtils.getKeySet(this.db);
    }

    public JsonObject getDb() {
        return db;
    }
}
