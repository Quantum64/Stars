package co.q64.stars.level.levels;

import co.q64.stars.level.Level;
import co.q64.stars.level.LevelType;
import co.q64.stars.type.forming.PurpleFormingBlockType;
import co.q64.stars.util.Structures;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PurpleLevel implements Level {
    protected @Inject Structures structures;
    protected @Inject @Getter PurpleFormingBlockType symbolicBlock;

    private final @Getter LevelType type = LevelType.PURPLE;

    protected @Inject PurpleLevel() {}

    public BlockPos createChallenge(ServerWorld world, BlockPos start) {
        return start;
    }
}
