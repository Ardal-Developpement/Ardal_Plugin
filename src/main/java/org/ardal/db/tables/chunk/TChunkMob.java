package org.ardal.db.tables.chunk;

import org.ardal.Ardal;
import org.ardal.models.MChunkMob;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TChunkMob {
    public boolean createChunkMob(@NotNull MChunkMob mChunkMob) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("insert into chunk_mob (chunk_id_group, mob_type, level, cooldown) values (?,?,?,?)"))
        {
            statement.setInt(1, mChunkMob.getChunkIdGroup());
            statement.setString(2, mChunkMob.getMobType());
            statement.setInt(3, mChunkMob.getLevel());
            statement.setFloat(4, mChunkMob.getCooldown());

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
                             "cooldown = ? " +
                             "where chunk_id_group = ?"))
        {
            statement.setInt(1, mChunkMob.getChunkIdGroup());
            statement.setString(2, mChunkMob.getMobType());
            statement.setInt(3, mChunkMob.getLevel());
            statement.setFloat(4, mChunkMob.getCooldown());


            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update chunk mob info in database.");
            e.printStackTrace();
        }

        return false;
    }

    public List<MChunkMob> getChunkMobsByChunkGroupId(int chunkIdGroup) {
        List<MChunkMob> chunks = new ArrayList<>();
        String request = "select mob_type, level, cooldown from chunk_mob where chunk_id_group = ?";
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement(request))
        {
            statement.setInt(1, chunkIdGroup);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    chunks.add(new MChunkMob(
                            chunkIdGroup,
                            resultSet.getString("mob_type"),
                            resultSet.getInt("level"),
                            resultSet.getFloat("cooldown")
                    ));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch chunk mobs by chunk id group in database.");
            e.printStackTrace();
        }

        return chunks;
    }
}
