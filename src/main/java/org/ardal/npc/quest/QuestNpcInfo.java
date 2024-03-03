package org.ardal.npc.quest;

import com.google.gson.JsonObject;
import org.ardal.Ardal;
import org.ardal.managers.QuestManager;

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
        this.questName = obj.get("questName").getAsString();
        this.questCoef = obj.get("questCoef").getAsInt();
        this.isShow = obj.get("isShow").getAsBoolean();
    }

    public JsonObject toJson(){
        JsonObject obj = new JsonObject();
        obj.addProperty("questName", this.questName);
        obj.addProperty("questCoef", this.questCoef);
        obj.addProperty("isShow", this.isShow);

        return obj;
    }

    public boolean isQuestDelete(){
        QuestManager questManager = Ardal.getInstance().getManager(QuestManager.class);
        return questManager.getQuestDeleteState(questName);
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
