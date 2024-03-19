package org.ardal.models;

public class MItemGroup {
    private int id_group;
    private String item_id;

    public MItemGroup(int id_group, String item_id) {
        this.id_group = id_group;
        this.item_id = item_id;
    }

    public int getIdGroup() {
        return id_group;
    }

    public void setIdGroup(int id_group) {
        this.id_group = id_group;
    }

    public String getItemId() {
        return item_id;
    }

    public void setItemId(String item_id) {
        this.item_id = item_id;
    }
}
