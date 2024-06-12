package org.ardal.db.tables.chunk;

import org.ardal.Ardal;
import org.ardal.models.MChunkMob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TChunkMob {
    public boolean createChunkMob(@NotNull MChunkMob mChunkMob) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("insert into chunk_mob (chunk_id_group, mob_type, level, cooldown, enable) values (?,?,?,?,?)"))
        {
            statement.setInt(1, mChunkMob.getChunkIdGroup());
            statement.setString(2, mChunkMob.getMobTypesAsString());
            statement.setInt(3, mChunkMob.getLevel());
            statement.setFloat(4, mChunkMob.getCooldown());
            statement.setBoolean(5, mChunkMob.isEnable());

            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save create chunk mob in database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateChunkMob(MChunkMob mChunkMob) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update chunk_mob set " +
                             "chunk_id_group = ?," +
                             "mob_type = ?," +
                             "level = ?," +
                             "cooldown = ?, " +
                             "enable = ? " +
                             "where chunk_id_group = ?"))
        {
            statement.setInt(1, mChunkMob.getChunkIdGroup());
            statement.setString(2, mChunkMob.getMobTypesAsString());
            statement.setInt(3, mChunkMob.getLevel());
            statement.setFloat(4, mChunkMob.getCooldown());
            statement.setBoolean(5, mChunkMob.isEnable());


            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update chunk mob info in database.");
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public MChunkMob getChunkMobByChunkGroupId(int chunkIdGroup) {
        String request = "select mob_type, level, cooldown, enable from chunk_mob where chunk_id_group = ?";

        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement(request))
        {
            statement.setInt(1, chunkIdGroup);

            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    return new MChunkMob(
                            chunkIdGroup,
                            resultSet.getString("mob_type"),
                            resultSet.getInt("level"),
                            resultSet.getFloat("cooldown"),
                            resultSet.getBoolean("enable")
                    );
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch chunk mobs by chunk id group in database.");
            e.printStackTrace();
        }

        return null;
    }
}
