package org.ardal.db.tables;

import org.ardal.Ardal;
import org.ardal.managers.CustomItemManager;
import org.ardal.models.MItemGroup;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TItemGroup {
    public void saveItemGroup(MItemGroup mItemGroup) {
        try {
            PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                    .prepareStatement("insert into item_group(item_id) values (?)");

            statement.setString(1, mItemGroup.getItemId());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e){
            Ardal.writeToLogger("Failed to save quest_player.");
            e.printStackTrace();
        }
    }

    public int saveItemsByGroupId(List<ItemStack> items){
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);

        int groupId = Ardal.getInstance().getDb().gettGroups().createGroup();
        if(groupId != -1) {

        try {
            PreparedStatement statement = Ardal.getInstance().getDb().getConnection()
                    .prepareStatement("insert into item_group(item_id, group_id) values (?,?)");

            for (ItemStack item : items) {
                statement.setString(1, customItemManager.addItem(item));
                statement.setInt(2, groupId);
                statement.executeUpdate();
            }

            statement.close();
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save quest_player.");
            e.printStackTrace();
            }
        }

        return groupId;
    }

    public List<String> getItemsByGroupId(int groupId) {
        List<String> items = new ArrayList<>();
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT item_id FROM item_group WHERE group_id = ?"))
        {

            statement.setInt(1, groupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    items.add(resultSet.getString("item_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
