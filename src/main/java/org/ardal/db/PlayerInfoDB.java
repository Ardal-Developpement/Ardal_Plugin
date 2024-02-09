package org.ardal.db;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.api.db.JsonDBStruct;
import org.ardal.objects.PlayerInfoObj;


import java.nio.file.Path;
import java.util.UUID;

public class PlayerInfoDB extends JsonDBStruct {
    private static final String DB_FILE_NAME = "PlayerInfoDB.json";

    public PlayerInfoDB(Path pluginDirPath) {
        super(pluginDirPath, DB_FILE_NAME);
    }

    public void addPlayer(PlayerInfoObj playerInfoCore){
        Ardal.writeToLogger("Adding " + playerInfoCore.getPlayerName() + " in " + this.getClass().getName());
        this.getDb().add(playerInfoCore.getPlayerUUID(), playerInfoCore.toJson());
        this.saveDB();
    }

    public PlayerInfoObj getPlayerInfo(UUID playerUUID){
        JsonObject playerInfoObj = this.getDb().getAsJsonObject(playerUUID.toString());
        return playerInfoObj == null ? null : new PlayerInfoObj(playerInfoObj);
    }
}
