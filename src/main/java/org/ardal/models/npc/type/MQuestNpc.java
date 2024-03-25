package org.ardal.models.npc.type;

import org.ardal.Ardal;

public class MQuestNpc {
    private String npc_uuid;
    private int quest_id;
    private int quest_coef;
    private boolean is_show;

    public MQuestNpc(String npc_uuid, int quest_id, int quest_coef, boolean is_show) {
        this.npc_uuid = npc_uuid;
        this.quest_id = quest_id;
        this.quest_coef = quest_coef;
        this.is_show = is_show;
    }

    public boolean updateQuestNpc(){
        return Ardal.getInstance().getDb().gettQuestNpc().updateQuestNpc(this);
    }

    public String getNpcUuid() {
        return npc_uuid;
    }

    public void setNpcUuid(String uuid) {
        this.npc_uuid = uuid;
    }

    public int getQuestId() {
        return quest_id;
    }

    public void setQuestId(int quest_id) {
        this.quest_id = quest_id;
    }

    public int getQuestCoef() {
        return quest_coef;
    }

    public void setQuestCoef(int quest_coef) {
        this.quest_coef = quest_coef;
    }

    public boolean getIsShow() {
        return is_show;
    }

    public void setIsShow(boolean is_show) {
        this.is_show = is_show;
    }
}
