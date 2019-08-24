package co.q64.stars.level.levels;

import co.q64.stars.block.ChallengeExitBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.GreyFormedBlock;
import co.q64.stars.level.Level;
import co.q64.stars.level.LevelType;
import co.q64.stars.type.forming.RedFormingBlockType;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RedLevel implements Level {
    protected @Inject GreyFormedBlock greyFormedBlock;
    protected @Inject DecayBlock decayBlock;
    protected @Inject DarknessBlock darknessBlock;
    protected @Inject ChallengeExitBlock challengeExitBlock;
    protected @Inject @Getter RedFormingBlockType symbolicBlock;

    private final @Getter LevelType type = LevelType.RED;

    protected @Inject RedLevel() {}

    public BlockPos createChallenge(ServerWorld world, BlockPos start) {
        box(world, decayBlock, start.add(-7, -8, -7), start.add(7, 20, 7));
        cube(world, greyFormedBlock, start.add(-2, -5, -2), start.add(2, 0, 2));
        cube(world, darknessBlock, start.add(-2, 5, -2), start.add(2, 10, 2));
        set(world, challengeExitBlock, start.add(0, 12, 0));
        return start;
    }
}
