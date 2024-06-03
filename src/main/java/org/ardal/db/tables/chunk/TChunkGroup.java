package org.ardal.db.tables.chunk;

import org.ardal.Ardal;
import org.ardal.models.MChunkGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TChunkGroup {
    public boolean createChunkGroup(MChunkGroup mChunkGroup) {
        int id = -1;
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement("insert into `chunks` (chunk_id, chunk_group_id) values (?,?)"))
        {
            statement.setLong(1, mChunkGroup.getChunkId());
            statement.setInt(2, mChunkGroup.getChunkIdGroup());

            statement.execute();
            statement.close();

            return true;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to create a new chunk group in the database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteChunkInGroup(Long chunkId) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from `chunk_id_group` WHERE chunk_id = ?"))
        {
            statement.setLong(1, chunkId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to delete chunk in group in the database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteChunkGroup(int chunkIdGroup) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from `chunk_id_group` WHERE chunk_id_group = ?"))
        {
            statement.setInt(1, chunkIdGroup);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to delete chunk group in the database.");
            e.printStackTrace();
        }

        return false;
    }

    public List<MChunkGroup> getChunkGroupByChunkGroupId(int chunkIdGroup) {
        List<MChunkGroup> chunks = new ArrayList<>();
        String request = "select chunk_id from chunk_id_group where chunk_id_group = ?";
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement(request))
        {
            statement.setInt(1, chunkIdGroup);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    chunks.add(new MChunkGroup(
                            resultSet.getLong("chunk_id"),
                            chunkIdGroup
                    ));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch chunks by chunk id group in database.");
            e.printStackTrace();
        }

        return chunks;
    }
}
