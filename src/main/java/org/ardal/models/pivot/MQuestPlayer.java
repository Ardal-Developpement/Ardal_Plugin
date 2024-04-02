package org.ardal.models.pivot;

import java.util.Date;

public class MQuestPlayer {
    private int quest_id;
    private String player_uuid;
    private boolean is_finished;
    private Date start_date;

    public MQuestPlayer(int quest_id, String player_uuid, boolean is_finished, Date start_date) {
        this.quest_id = quest_id;
        this.player_uuid = player_uuid;
        this.is_finished = is_finished;
        this.start_date = start_date;
    }

    public int getQuestId() {
        return quest_id;
    }

    public void setQuestId(int quest_id) {
        this.quest_id = quest_id;
    }

    public String getPlayerUuid() {
        return player_uuid;
    }

    public void setPlayerUuid(String player_uuid) {
        this.player_uuid = player_uuid;
    }

    public boolean getIsFinished() {
        return is_finished;
    }

    public void setIsFinished(boolean is_finished) {
        this.is_finished = is_finished;
    }

    public Date getStartDate() {
        return start_date;
    }

    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }
}
