package co.q64.stars.level.levels;

import co.q64.stars.level.Level;
import co.q64.stars.level.LevelType;
import co.q64.stars.type.forming.RedFormingBlockType;
import co.q64.stars.util.Structures;
import co.q64.stars.util.Structures.StructureType;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RedLevel implements Level {
    protected @Inject Structures structures;
    protected @Inject @Getter RedFormingBlockType symbolicBlock;

    private final @Getter LevelType type = LevelType.RED;

    protected @Inject RedLevel() {}

    public BlockPos createChallenge(ServerWorld world, BlockPos start) {
        return structures.get(StructureType.CHALLENGE_RED).placeChallenge(world, start);
    }
}
