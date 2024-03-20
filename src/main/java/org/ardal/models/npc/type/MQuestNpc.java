package org.ardal.models.npc.type;

public class MQuestNpc {
    private String uuid;
    private int quest_id;
    private int quest_coef;
    private boolean is_show;

    public MQuestNpc(String uuid, int quest_id, int quest_coef, boolean is_show) {
        this.uuid = uuid;
        this.quest_id = quest_id;
        this.quest_coef = quest_coef;
        this.is_show = is_show;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getQuestId() {
        return quest_id;
    }

    public void setQuest_id(int quest_id) {
        this.quest_id = quest_id;
    }

    public int getQuestCoef() {
        return quest_coef;
    }

    public void setQuest_coef(int quest_coef) {
        this.quest_coef = quest_coef;
    }

    public boolean getIsShow() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }
}
