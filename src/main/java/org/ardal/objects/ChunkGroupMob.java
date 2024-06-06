package org.ardal.objects;

import org.ardal.Ardal;
import org.ardal.models.MChunkMob;
import org.bukkit.entity.Player;

public class ChunkGroupMob extends ChunkGroupObj{
    private final MChunkMob mChunkMob;

    public ChunkGroupMob(MChunkMob mChunkMob) {
        super(mChunkMob.getChunkIdGroup());
        Ardal.getInstance().getDb().gettChunkMob().createChunkMob(mChunkMob);
        this.mChunkMob = mChunkMob;
    }

    public ChunkGroupMob(int chunkGroupModId) {
        super(chunkGroupModId);
        this.mChunkMob = Ardal.getInstance().getDb().gettChunkMob().getChunkMobByChunkGroupId(chunkGroupModId);
        if(this.mChunkMob == null) {
            System.err.println("MChunkMob is null! with chunkGroupModId: " + chunkGroupModId);
        }
    }


    @Override
    public void onPlayerEnter(Player player) {
        player.sendMessage("You enter in chunk group mob" + this.getChunkGroupId());
    }
}
