package org.ardal.models;

public class MQuest {
    private String name;
    private int book_id;
    private int request_item_id;
    private int reward_item_id;
    private boolean is_active;
    private boolean is_delete;

    public MQuest(String name, int book_id, int request_item_id, int reward_item_id, boolean is_active, boolean is_delete) {
        this.name = name;
        this.book_id = book_id;
        this.request_item_id = request_item_id;
        this.reward_item_id = reward_item_id;
        this.is_active = is_active;
        this.is_delete = is_delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getRequest_item_id() {
        return request_item_id;
    }

    public void setRequest_item_id(int request_item_id) {
        this.request_item_id = request_item_id;
    }

    public int getReward_item_id() {
        return reward_item_id;
    }

    public void setReward_item_id(int reward_item_id) {
        this.reward_item_id = reward_item_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
