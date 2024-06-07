package org.ardal.db.tables.chunk;

import org.ardal.Ardal;
import org.ardal.models.MChunkGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TChunkGroup {
    public int createChunkGroup(@NotNull MChunkGroup mChunkGroup) {
        int id = -1;
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("insert into chunk_group (chunk_id_group, modifiers) values (null,?)", Statement.RETURN_GENERATED_KEYS))
        {
            statement.setString(1, mChunkGroup.getModifierTypesAsString());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save create chunk group in database.");
            e.printStackTrace();
        }

        return id;
    }

    public boolean updateChunk(MChunkGroup mChunkGroup) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update chunk_group set " +
                             "modifiers = ? " +
                             "where chunk_id_group = ?"))
        {
            statement.setString(1, mChunkGroup.getModifierTypesAsString());
            statement.setLong(2, mChunkGroup.getChunkIdGroup());



            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update chunk group info in database.");
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public MChunkGroup getChunkGroupById(int chunkGroupId) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT modifiers FROM chunk_group WHERE chunk_id_group = ?"))
        {
            statement.setInt(1, chunkGroupId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new MChunkGroup(
                            chunkGroupId,
                            resultSet.getString("modifiers")
                    );
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch chunk group by group id in database.");
            e.printStackTrace();
        }
        return null;
    }

    public List<MChunkGroup> getAllChunkGroups() {
        List<MChunkGroup> chunkGroups = new ArrayList<>();
        String request = "select chunk_id_group, modifiers from chunk_group";
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement(request))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    chunkGroups.add(new MChunkGroup(
                            resultSet.getInt("chunk_id_group"),
                            resultSet.getString("modifiers")));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch chunk groups in database.");
            e.printStackTrace();
        }

        return chunkGroups;
    }
}
