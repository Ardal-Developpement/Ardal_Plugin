package org.ardal.objects.chunk.modifiers;

import org.ardal.Ardal;
import org.ardal.api.chunks.ChunkModifierType;
import org.ardal.api.entities.mobs.MobType;
import org.ardal.models.MChunkMob;
import org.ardal.objects.chunk.ChunkGroupObj;
import org.ardal.objects.chunk.ChunkModifier;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class ChunkMobModifier extends ChunkModifier {
    private final HashSet<MobType> spawningMobTypes;

    private MChunkMob mChunkMob;

    public ChunkMobModifier(ChunkGroupObj chunkGroupObj) {
        super(chunkGroupObj, ChunkModifierType.MOB);

        this.mChunkMob = Ardal.getInstance().getDb().gettChunkMob()
                .getChunkMobByChunkGroupId(this.getChunkGroupObj().getChunkGroupId());

        if(this.mChunkMob == null) {
            Ardal.getInstance().getLogger().info(
                    "Creating new mChunkMob for chunk group id: " + this.getChunkGroupObj().getChunkGroupId()
            );

            this.mChunkMob = new MChunkMob(
                    this.getChunkGroupObj().getChunkGroupId(),
                    "",
                    0,
                    15,
                    false
            );

            Ardal.getInstance().getDb().gettChunkMob().createChunkMob(this.mChunkMob);
        }

        this.spawningMobTypes = new HashSet<>(this.mChunkMob.getMobTypes());
    }

    @Override
    public void onPlayerEnter(Player player) {
        player.sendMessage("You enter in chunk group mob: " + this.getChunkGroupObj().getChunkGroupId());
    }

    public boolean addSpawningMob(MobType mobType) {
        if(this.hasMobType(mobType)) {
            Ardal.getInstance().getLogger().severe("Try adding a spawning mob that has already been set.!");
            return false;
        }

        this.spawningMobTypes.add(mobType);

        this.mChunkMob.addSpawningMob(mobType);
        return this.mChunkMob.updateChunkMob();
    }

    public boolean removeSpawningMob(MobType mobType) {
        if(!this.hasMobType(mobType)) {
            Ardal.getInstance().getLogger().severe("Try removing a spawning mob that has already been set.!");
            return false;
        }

        this.spawningMobTypes.remove(mobType);

        this.mChunkMob.removeSpawningMob(mobType);
        return this.mChunkMob.updateChunkMob();
    }

    public boolean hasMobType(MobType mobType) {
        return this.spawningMobTypes.contains(mobType);
    }
}
