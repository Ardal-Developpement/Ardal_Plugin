package org.ardal.objects;

import org.ardal.Ardal;
import org.ardal.models.MChunk;
import org.bukkit.entity.Player;

import java.util.List;

public class ChunkGroupObj {
    private final int chunkGroupId;
    public final List<MChunk> chunks;

    public ChunkGroupObj(int chunkGroupId) {
        this.chunkGroupId = chunkGroupId;
        this.chunks = Ardal.getInstance().getDb().gettChunk().getAllChunksByGroupId(this.chunkGroupId);
    }

    public void onPlayerEnter(Player player) {
        player.sendMessage("You enter in chunk group " + this.chunkGroupId);
    }

    public boolean addChunkInGroup(MChunk chunk) {
        if(Ardal.getInstance().getDb().gettChunk().createChunk(chunk)) {
            this.chunks.add(chunk);
            return true;
        }

        return false;
    }

    public boolean deleteChunkInGroup(MChunk chunk) {
        if(this.chunks.contains(chunk)) {
            if(Ardal.getInstance().getDb().gettChunk().deleteChunkById(chunk.getChunkId())) {
                this.chunks.remove(chunk);
                return true;
            }
        }

        return false;
    }

    public int getChunkGroupId() {
        return chunkGroupId;
    }

    public List<MChunk> getChunks() {
        return chunks;
    }

    public boolean containChunk(Long chunkId) {
        for(MChunk chunk : this.chunks) {
            if(chunk.getChunkId().equals(chunkId)) {
                return true;
            }
        }

        return false;
    }
}
