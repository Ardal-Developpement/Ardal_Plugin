package org.ardal.objects.chunk.modifiers;

import org.ardal.objects.chunk.ChunkGroupObj;
import org.ardal.objects.chunk.ChunkModifier;
import org.bukkit.entity.Player;

public class ChunkMobModifier extends ChunkModifier {
    public ChunkMobModifier(ChunkGroupObj chunkGroupObj) {
        super(chunkGroupObj);
    }

    @Override
    public void onPlayerEnter(Player player) {
        player.sendMessage("You enter in chunk group mob: " + this.getChunkGroupObj().getChunkGroupId());
    }
}
