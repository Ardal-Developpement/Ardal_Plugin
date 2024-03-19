package org.ardal.db.tables;

import org.ardal.Ardal;
import org.ardal.models.MItemGroup;

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

            statement.execute();
            statement.close();
        } catch (SQLException e){
            Ardal.writeToLogger("Failed to save quest_player.");
            e.printStackTrace();
        }
    }

    public List<MItemGroup> getItemsByGroupId(int groupId) {
        List<MItemGroup> mItemGroups = new ArrayList<>();
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT item_group_id, item_id FROM item_group WHERE item_group_id = ?"))
        {

            statement.setInt(1, groupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    mItemGroups.add(new MItemGroup(
                            resultSet.getInt("item_group_id"),
                            resultSet.getString("item_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mItemGroups;
    }
}
