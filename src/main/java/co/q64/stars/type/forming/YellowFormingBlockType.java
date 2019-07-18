package co.q64.stars.type.forming;

import co.q64.stars.block.YellowFormedBlock;
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
public class YellowFormingBlockType implements FormingBlockType {
    private final @Getter int id = 0;
    private final @Getter String name = "yellow";
    private final @Getter int buildTime = 200;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 200, g = 200, b = 20;

    protected @Getter @Inject YellowFormedBlock formedBlock;

    protected @Inject YellowFormingBlockType() {}

    public int getIterations(long seed) {
        return (int) (5 + (seed % 4));
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        Direction result = Direction.UP;
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == direction.DOWN) {
                continue;
            }
            if (hasBlock(world, position, direction)) {
                if (!hasBlock(world, position, direction.getOpposite())) {
                    result = direction.getOpposite();
                }
            }
        }
        return result;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        if (!hasBlock(world, position, last)) {
            return Collections.singletonList(last);
        }
        return Collections.emptyList();
    }

    public int getDecayTime(long seed) {
        return 40 + (int) seed % 10;
    }
}
