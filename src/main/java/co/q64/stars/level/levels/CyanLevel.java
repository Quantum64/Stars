package co.q64.stars.level.levels;

import co.q64.stars.level.Level;
import co.q64.stars.level.LevelType;
import co.q64.stars.type.forming.CyanFormingBlockType;
import co.q64.stars.util.Structures;
import co.q64.stars.util.Structures.StructureType;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CyanLevel implements Level {
    protected @Inject Structures structures;
    protected @Inject @Getter CyanFormingBlockType symbolicBlock;

    private final @Getter LevelType type = LevelType.CYAN;

    protected @Inject CyanLevel() {}

    public BlockPos createChallenge(ServerWorld world, BlockPos start) {
        return structures.get(StructureType.CHALLENGE_CYAN).placeChallenge(world, start);
    }
}
