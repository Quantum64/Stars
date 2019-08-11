package co.q64.stars.type.forming;

import co.q64.stars.block.BlueFormedBlock.BlueFormedBlockHard;
import co.q64.stars.block.BrownFormedBlock;
import co.q64.stars.block.BrownFormedBlock.BrownFormedBlockHard;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.item.BrownSeedItem;
import co.q64.stars.qualifier.SoundQualifiers.Brown;
import co.q64.stars.type.FormingBlockType;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Singleton
public class BrownFormingBlockType implements FormingBlockType {
    private static final Direction[] DIRECTIONS = Direction.values();

    private final @Getter int id = 6;
    private final @Getter String name = "brown";
    private final @Getter int buildTime = 2300;
    private final @Getter int buildTimeOffset = 300;
    private final @Getter float r = 100, g = 70, b = 27;

    protected @Getter @Inject BrownFormedBlock formedBlock;
    protected @Getter @Inject BrownFormedBlockHard formedBlockHard;
    protected @Getter @Inject Provider<BrownSeedItem> itemProvider;
    protected @Getter @Inject @Brown Set<SoundEvent> sounds;

    protected @Inject BrownFormingBlockType() {}

    public int getIterations(long seed) {
        return 100;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        Direction result = Direction.UP;
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == direction.DOWN) {
                continue;
            }
            if (!hasBlock(world, position, direction)) {
                result = direction;
            }
        }
        return result;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        /*
        if (iterations == 0) {
            if (!hasBlock(world, position, Direction.DOWN)) {
                return Arrays.asList(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH, Direction.DOWN);
            }
        }
         */
        List<Direction> result = new ArrayList<>();
        BlockState state = world.getBlockState(position.offset(Direction.DOWN));
        if (!(state.getBlock() instanceof BrownFormedBlock || state.getBlock() instanceof FormingBlock || state.isAir(world, position.offset(Direction.DOWN)))) {
            for (Direction direction : DIRECTIONS) {
                if (direction == Direction.UP || direction == Direction.DOWN) {
                    continue;
                }
                if (!hasBlock(world, position, direction)) {
                    result.add(direction);
                }
            }
        }
        if (!hasBlock(world, position, Direction.DOWN)) {
            result.add(Direction.DOWN);
        }
        for (int y = 1; y < 7; y++) {
            if (!(world.getBlockState(position.offset(Direction.UP, y)).getBlock() instanceof BrownFormedBlock)) {
                return result;
            }

        }
        return Collections.emptyList();
    }

    public int getDecayTime(long seed) {
        return 150 + (int) (seed % 10);
    }
}
