package co.q64.stars.level.levels;

import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.GreyFormedBlock;
import co.q64.stars.level.Level;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RedLevel implements Level {
    private static final int DISTANCE = 20;

    protected @Inject GreyFormedBlock greyFormedBlock;
    protected @Inject DecayBlock decayBlock;

    protected @Inject RedLevel() {}

    public List<BlockPos> getChallengeStars(BlockPos start) {
        List<BlockPos> result = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                continue;
            }
            result.add(start.offset(direction, DISTANCE));
        }
        return result;
    }

    public void createChallenge(ServerWorld world, BlockPos start) {
        box(world, decayBlock, start.add(-7, -8, -7), start.add(7, 17, 7));
        cube(world, greyFormedBlock, start.add(-2, -5, -2), start.add(2, 0, 2));
        cube(world, decayBlock, start.add(-2, 5, -2), start.add(2, 10, 2));
    }

    public BlockPos getHeartLocation(BlockPos start) {
        return start.add(0, 15, 0);
    }
}
