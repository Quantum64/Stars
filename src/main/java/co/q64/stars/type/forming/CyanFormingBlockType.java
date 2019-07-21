package co.q64.stars.type.forming;

import co.q64.stars.block.CyanFormedBlock;
import co.q64.stars.item.BlueSeedItem;
import co.q64.stars.item.CyanSeedItem;
import co.q64.stars.type.FormingBlockType;
import lombok.Getter;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class CyanFormingBlockType implements FormingBlockType {
    private final @Getter int id = 4;
    private final @Getter String name = "cyan";
    private final @Getter int buildTime = 1000;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 181, g = 255, b = 251;

    protected @Getter @Inject CyanFormedBlock formedBlock;
    protected @Getter @Inject Provider<CyanSeedItem> itemProvider;

    protected @Inject CyanFormingBlockType() {}

    public int getIterations(long seed) {
        return 1;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        if (!hasBlock(world, position, Direction.UP)) {
            return Direction.UP;
        }
        return null;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        List<Direction> result = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            //if (!hasBlock(world, position, direction)) {
            result.add(direction);
            //}
        }
        return result;
    }

    public int getDecayTime(long seed) {
        return 600;
    }
}
