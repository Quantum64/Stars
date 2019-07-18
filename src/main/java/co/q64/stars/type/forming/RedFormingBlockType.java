package co.q64.stars.type.forming;

import co.q64.stars.block.RedFormedBlock;
import co.q64.stars.type.FormingBlockType;
import lombok.Getter;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RedFormingBlockType implements FormingBlockType {
    private final @Getter int id = 5;
    private final @Getter String name = "red";
    private final @Getter int buildTime = 50;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 255, g = 0, b = 0;

    protected @Getter @Inject RedFormedBlock formedBlock;

    protected @Inject RedFormingBlockType() {}

    public int getIterations(long seed) {
        return 4;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        if (!hasBlock(world, position, Direction.UP)) {
            return Direction.UP;
        }
        return null;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        if (iterations >= 1) {
            List<Direction> result = new ArrayList<>();
            for (Direction direction : Direction.values()) {
                if (world.getBlockState(position.offset(direction)).getBlock() != formedBlock) {
                    result.add(direction);
                }
            }
            return result;
        }
        List<Direction> result = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (direction == last.getOpposite()) {
                continue;
            }
            if (world.getBlockState(position.offset(direction)).getBlock() != formedBlock) {
                result.add(direction);
            }
        }
        return result;
    }

    public int getDecayTime(long seed) {
        return 50 + (int) seed % 10;
    }
}
