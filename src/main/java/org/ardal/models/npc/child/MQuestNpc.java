package org.ardal.models.npc.child;

public class MQuestNpc {
    private String uuid;
    private String quest_name;
    private int quest_coef;
    private boolean is_show;

    public MQuestNpc(String uuid, String quest_name, int quest_coef, boolean is_show) {
        this.uuid = uuid;
        this.quest_name = quest_name;
        this.quest_coef = quest_coef;
        this.is_show = is_show;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQuest_name() {
        return quest_name;
    }

    public void setQuest_name(String quest_name) {
        this.quest_name = quest_name;
    }

    public int getQuest_coef() {
        return quest_coef;
    }

    public void setQuest_coef(int quest_coef) {
        this.quest_coef = quest_coef;
    }

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }
}
