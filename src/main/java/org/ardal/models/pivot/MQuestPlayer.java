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

    public int getQuest_id() {
        return quest_id;
    }

    public void setQuest_id(int quest_id) {
        this.quest_id = quest_id;
    }

    public String getPlayer_uuid() {
        return player_uuid;
    }

    public void setPlayer_uuid(String player_uuid) {
        this.player_uuid = player_uuid;
    }

    public boolean isIs_finished() {
        return is_finished;
    }

    public void setIs_finished(boolean is_finished) {
        this.is_finished = is_finished;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
}
