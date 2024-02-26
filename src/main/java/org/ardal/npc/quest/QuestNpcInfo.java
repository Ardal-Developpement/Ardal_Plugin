package org.ardal.npc.quest;

import com.google.gson.JsonObject;

public class QuestNpcInfo {
    private String questName;
    private int questCoef;
    private boolean isShow;

    public QuestNpcInfo(String questName, int questCoef, boolean isShow){
        this.questName = questName;
        this.questCoef = questCoef;
        this.isShow = isShow;
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

    public void setIsShow(boolean state) {
        isShow = state;
    }

    public boolean getIsShow() {
        return this.isShow;
    }
}
