package org.ardal.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    public static void createDirIfNotExist(Path path){
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveJsonAt(JsonObject jsonObject, Path filePath){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(filePath.toFile())) {
            if(!Files.exists(filePath.getParent())){
                Files.createDirectories(filePath.getParent());
            }

            JsonWriter jsonWriter = new JsonWriter(fileWriter);
            jsonWriter.setIndent("\t");
            gson.toJson(jsonObject, jsonWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject loadJsonFromPath(Path filePath){
        try {
            String data = new String(Files.readAllBytes(filePath));
            Gson gson = new Gson();

            return gson.fromJson(data, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getKeySet(JsonObject jsonObject){
        List<String> keySet = new ArrayList<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            keySet.add(entry.getKey());
        }

        return keySet;
    }

    public static List<String> jsonArrayToStrList(JsonArray array){
        List<String> list = new ArrayList<>();
        for(JsonElement elem : array){
            list.add(elem.getAsString());
        }

        return list;
    }

    public static List<String> jsonArrayToStrList(JsonElement elem){
        return jsonArrayToStrList(elem.getAsJsonArray());
    }

    public static JsonArray jsonArrayFromStrList(List<String> list){
        JsonArray jsonArray = new JsonArray();
        for(String listObj : list){
            jsonArray.add(listObj);
        }

        return jsonArray;
    }
}
