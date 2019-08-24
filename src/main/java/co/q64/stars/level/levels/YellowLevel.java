package co.q64.stars.level.levels;

import co.q64.stars.level.Level;
import co.q64.stars.level.LevelType;
import co.q64.stars.type.forming.PurpleFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import co.q64.stars.util.Structures;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class YellowLevel implements Level {
    protected @Inject Structures structures;
    protected @Inject @Getter YellowFormingBlockType symbolicBlock;

    private final @Getter LevelType type = LevelType.YELLOW;

    protected @Inject YellowLevel() {}

    public BlockPos createChallenge(ServerWorld world, BlockPos start) {
        return start;
    }
}
