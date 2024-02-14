package org.ardal.npc.quest;

import com.google.gson.JsonObject;

public class QuestNpcInfo {
    private String questName;
    private int questCoef;

    public QuestNpcInfo(String questName, int questCoef){
        this.questName = questName;
        this.questCoef = questCoef;
    }

    public QuestNpcInfo(JsonObject obj){
        this.questName = obj.getAsJsonObject("questName").getAsString();
        this.questCoef = obj.getAsJsonObject("questCoef").getAsInt();
    }

    public JsonObject toJson(){
        JsonObject obj = new JsonObject();
        obj.addProperty("questName", this.questName);
        obj.addProperty("questCoef", this.questCoef);

        return obj;
    }

    public String getQuestName() {
        return questName;
    }

    public int getQuestCoef() {
        return questCoef;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public void setQuestCoef(int questCoef) {
        this.questCoef = questCoef;
    }
}
