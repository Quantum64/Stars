package co.q64.stars.level.levels;

import co.q64.stars.level.Level;
import co.q64.stars.level.LevelType;
import co.q64.stars.util.Structures;
import co.q64.stars.util.Structures.StructureType;
import lombok.Getter;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CyanLevel implements Level {
    private static final int DISTANCE = 35;

    protected @Inject Structures structures;

    private final @Getter LevelType type = LevelType.CYAN;

    protected @Inject CyanLevel() {}

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

    public BlockPos createChallenge(ServerWorld world, BlockPos start) {
        return structures.get(StructureType.CHALLENGE_CYAN).placeChallenge(world, start);
    }
}
