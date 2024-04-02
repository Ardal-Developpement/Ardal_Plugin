package org.ardal.models;

import org.ardal.Ardal;

public class MQuest {
    private int id;
    private String name;
    private String book_id;
    private String synopsis;
    private int request_item_group_id;
    private int reward_item_group_id;
    private boolean is_active;
    private boolean is_delete;



    public MQuest(int id, String name, String book_id, String synopsis, int request_item_group_id, int reward_item_group_id, boolean is_active, boolean is_delete) {
        this.id = id;
        this.name = name;
        this.book_id = book_id;
        this.synopsis = synopsis;
        this.request_item_group_id = request_item_group_id;
        this.reward_item_group_id = reward_item_group_id;
        this.is_active = is_active;
        this.is_delete = is_delete;
    }

    public boolean updateQuest(){
        return Ardal.getInstance().getDb().gettQuest().updateQuest(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookId() {
        return book_id;
    }

    public void setBookId(String book_id) {
        this.book_id = book_id;
    }

    public int getRequestItemGroupId() {
        return request_item_group_id;
    }

    public void setRequestItemGroupId(int request_item_group_id) {
        this.request_item_group_id = request_item_group_id;
    }

    public int getRewardItemGroupId() {
        return reward_item_group_id;
    }

    public void setRewardItemGroupId(int reward_item_group_id) {
        this.reward_item_group_id = reward_item_group_id;
    }

    public boolean getIsActive() {
        return is_active;
    }

    public void setIsActive(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean getIsDelete() {
        return is_delete;
    }

    public void setIsDelete(boolean is_delete) {
        this.is_delete = is_delete;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
