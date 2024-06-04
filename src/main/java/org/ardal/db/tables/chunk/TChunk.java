package org.ardal.db.tables.chunk;

import org.ardal.Ardal;
import org.ardal.models.MChunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TChunk {
    public boolean createChunk(@NotNull MChunk mChunk) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("insert into chunks (chunk_id, chunk_group_id) values (?,?)"))
        {
            statement.setLong(1, mChunk.getChunckId());
            statement.setInt(2, mChunk.getChunkGroupId());

            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to save create chunk in database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateChunk(MChunk mChunk) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("update chunks set " +
                             "chunk_group_id = ? " +
                             "where chunk_id = ?"))
        {
            statement.setInt(1, mChunk.getChunkGroupId());
            statement.setLong(2, mChunk.getChunckId());


            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to update chunk info in database.");
            e.printStackTrace();
        }

        return false;
    }

    @Nullable
    public MChunk getChunkById(Long chunkId) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT chunk_group_id FROM chunks WHERE chunk_id = ?"))
        {
            statement.setLong(1, chunkId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new MChunk(
                            chunkId,
                            resultSet.getInt("chunk_group_id")
                    );
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch chunk by id in database.");
            e.printStackTrace();
        }
        return null;
    }

    public List<MChunk> getAllChunksByGroupId(int groupId) {
        List<MChunk> chunks = new ArrayList<>();
        String request = "select chunk_id from chunks where chunk_group_id = ?";
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement(request))
        {
            statement.setInt(1, groupId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    chunks.add(new MChunk(
                            resultSet.getLong("chunk_id"),
                            groupId
                    ));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch chunks by group id in database.");
            e.printStackTrace();
        }

        return chunks;
    }

    public List<MChunk> getAllChunks() {
        List<MChunk> chunks = new ArrayList<>();
        String request = "select chunk_id, chunk_group_id from chunks";
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection.prepareStatement(request))
        {
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    chunks.add(new MChunk(
                            resultSet.getLong("chunk_id"),
                            resultSet.getInt("chunk_group_id")
                    ));
                }
            }
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to fetch chunks in database.");
            e.printStackTrace();
        }

        return chunks;
    }

    public boolean deleteChunkById(Long chunkId) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from chunks WHERE chunk_id = ?"))
        {
            statement.setLong(1, chunkId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to delete chunk by id in database.");
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteChunkGroup(int chunkGroupId) {
        try (Connection connection = Ardal.getInstance().getDb().getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("delete from chunks WHERE chunk_group_id = ?"))
        {
            statement.setInt(1, chunkGroupId);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            Ardal.writeToLogger("Failed to delete chunk group in database.");
            e.printStackTrace();
        }

        return false;
    }
}
