package co.q64.stars.type.forming;

import co.q64.stars.block.BrownFormedBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.item.BrownSeedItem;
import co.q64.stars.type.FormingBlockType;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class BrownFormingBlockType implements FormingBlockType {
    private static final Direction[] DIRECTIONS = Direction.values();

    private final @Getter int id = 6;
    private final @Getter String name = "brown";
    private final @Getter int buildTime = 700;
    private final @Getter int buildTimeOffset = 150;
    private final @Getter float r = 173, g = 85, b = 0;

    protected @Getter @Inject BrownFormedBlock formedBlock;
    protected @Getter @Inject Provider<BrownSeedItem> itemProvider;

    protected @Inject BrownFormingBlockType() {}

    public int getIterations(long seed) {
        return 8;
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
        if (iterations == 0) {
            if (!hasBlock(world, position, Direction.DOWN)) {
                return Arrays.asList(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.DOWN);
            }
        }
        List<Direction> result = new ArrayList<>();
        for (Direction direction : DIRECTIONS) {
            if (direction == Direction.UP || direction == direction.DOWN) {
                continue;
            }
            boolean canPlace = false;
            for (Direction d : DIRECTIONS) {
                if (d == Direction.UP || d == direction.DOWN) {
                    continue;
                }
                BlockPos target = position.offset(direction).offset(d).offset(Direction.DOWN);
                BlockState state = world.getBlockState(target);
                if (state.getBlock() instanceof BrownFormedBlock || state.getBlock() instanceof FormingBlock || state.isAir(world, target)) {
                    continue;
                }
                canPlace = true;
            }
            if (!hasBlock(world, position, direction) && canPlace) {
                result.add(direction);
            }
        }
        if (!hasBlock(world, position, Direction.DOWN)) {
            result.add(Direction.DOWN);
        }
        return result;
    }

    public int getDecayTime(long seed) {
        return 250 + (int) seed % 50;
    }
}
