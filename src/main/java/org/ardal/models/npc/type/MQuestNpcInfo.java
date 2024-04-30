package org.ardal.models.npc.type;

import org.ardal.Ardal;

public class MQuestNpcInfo {
    private String npc_uuid;
    private int nb_quest_show;

    public MQuestNpcInfo(String npc_uuid, int nb_quest_show) {
        this.npc_uuid = npc_uuid;
        this.nb_quest_show = nb_quest_show;
    }

    public boolean updateQuestNpcInfo(){
        return Ardal.getInstance().getDb().gettQuestNpcInfo().updateQuestNpcInfo(this);
    }


    public String getNpcUuid() {
        return npc_uuid;
    }

    public void setNpcUuid(String npc_uuid) {
        this.npc_uuid = npc_uuid;
    }

    public int getNbQuestShow() {
        return nb_quest_show;
    }

    public void setNbQuestShow(int nb_quest_show) {
        this.nb_quest_show = nb_quest_show;
    }
}
