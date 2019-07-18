package co.q64.stars.type.forming;

import co.q64.stars.block.BlueFormedBlock;
import co.q64.stars.type.FormingBlockType;
import lombok.Getter;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;

@Singleton
public class BlueFormingBlockType implements FormingBlockType {
    private final @Getter int id = 3;
    private final @Getter String name = "blue";
    private final @Getter int buildTime = 4500;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 18, g = 0, b = 255;

    protected @Getter @Inject BlueFormedBlock formedBlock;

    protected @Inject BlueFormingBlockType() {}

    public int getIterations(long seed) {
        return 0;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        if (!hasBlock(world, position, Direction.UP)) {
            return Direction.UP;
        }
        return null;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        return Collections.emptyList();
    }

    public int getDecayTime(long seed) {
        return 200;
    }
}