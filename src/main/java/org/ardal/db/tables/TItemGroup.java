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
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                 .prepareStatement("insert into item_group(item_id) values (?)"))
        {
            statement.setString(1, mItemGroup.getItemId());
            statement.executeUpdate();
        } catch (SQLException e){
            Ardal.writeToLogger("Failed to save quest_player.");
            e.printStackTrace();
        }
    }

    public int saveItemsByGroupId(List<ItemStack> items){
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);

        int groupId = Ardal.getInstance().getDb().gettGroups().createGroup();
        if(groupId != -1) {

        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                 .prepareStatement("insert into item_group(item_id, group_id) values (?,?)"))
        {
            for (ItemStack item : items) {
                statement.setString(1, customItemManager.addItem(item));
                statement.setInt(2, groupId);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save quest_player.");
            e.printStackTrace();
            }
        }

        return groupId;
    }

    public boolean deleteItemsByGroupId(int groupId) {
        CustomItemManager customItemManager = Ardal.getInstance().getManager(CustomItemManager.class);
        List<String> itemIdsToDelete = new ArrayList<>();

        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement selectStatement = connection.prepareStatement("SELECT item_id FROM item_group WHERE group_id = ?");
             PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM item_group WHERE group_id = ?")) {

            selectStatement.setInt(1, groupId);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String itemId = resultSet.getString("item_id");
                itemIdsToDelete.add(itemId);
            }
            resultSet.close();

            deleteStatement.setInt(1, groupId);
            int rowsAffected = deleteStatement.executeUpdate();

            if (rowsAffected > 0) {
                customItemManager.removeItems(itemIdsToDelete);
                return true;
            }

        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to delete items by group in the database.");
            e.printStackTrace();
        }

        return false;
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
            Ardal.writeToLogger("Failed to get items by group id in the database.");
            e.printStackTrace();
        }

        return items;
    }
}
