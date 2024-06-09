package org.ardal.objects.chunk;

import org.ardal.Ardal;
import org.ardal.api.chunks.ChunkModifierType;
import org.ardal.db.Database;
import org.ardal.models.MChunk;
import org.ardal.objects.chunk.modifiers.ChunkMobModifier;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ChunkGroupObj {
    private final int chunkGroupId;
    private final List<MChunk> chunks;
    private final List<ChunkModifier> modifiers;

    public ChunkGroupObj(int chunkGroupId) {
        Database db = Ardal.getInstance().getDb();

        this.chunkGroupId = chunkGroupId;
        this.chunks = db.gettChunk().getAllChunksByGroupId(this.chunkGroupId);
        this.modifiers = new ArrayList<>();

        for(ChunkModifierType type : db.gettChunkGroup().getChunkGroupById(this.chunkGroupId).getModifierTypes()) {
            switch(type) {
                case MOB:
                    this.modifiers.add(new ChunkMobModifier(this));
                    break;
                default:
            }
        }
    }

    public void onPlayerEnter(Player player) {
        player.sendMessage("You enter in chunk group " + this.chunkGroupId);
        this.modifiers.forEach(modifier -> modifier.onPlayerEnter(player));
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

    public List<ChunkModifier> getModifiers() {
        return modifiers;
    }

    public List<ChunkModifierType> getModifierTypes() {
        List<ChunkModifierType> types = new ArrayList<>();
        for(ChunkModifier modifiers : this.getModifiers()) {
            types.add(modifiers.getChunkModifierType());
        }

        return types;
    }
}
