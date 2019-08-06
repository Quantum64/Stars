package co.q64.stars.type.forming;

import co.q64.stars.block.PurpleFormedBlock;
import co.q64.stars.item.PurpleSeedItem;
import co.q64.stars.qualifier.SoundQualifiers.Purple;
import co.q64.stars.type.FormingBlockType;
import lombok.Getter;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Singleton
public class PurpleFormingBlockType implements FormingBlockType {
    private final @Getter int id = 1;
    private final @Getter String name = "purple";
    private final @Getter int buildTime = 2500;
    private final @Getter int buildTimeOffset = 200;
    private final @Getter float r = 141, g = 49, b = 194;

    protected @Getter @Inject PurpleFormedBlock formedBlock;
    protected @Getter @Inject Provider<PurpleSeedItem> itemProvider;
    protected @Getter @Inject @Purple Set<SoundEvent> sounds;

    protected @Inject PurpleFormingBlockType() {}

    public int getIterations(long seed) {
        return 3;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        Direction result = null;
        if (!hasBlock(world, position, Direction.UP)) {
            result = Direction.UP;
        }
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
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
        List<Direction> result = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (direction.getAxis() != last.getAxis()) {
                if (!hasBlock(world, position, direction)) {
                    result.add(direction);
                }
            }
        }
        return result;
    }

    public int getDecayTime(long seed) {
        return 150 + (int) seed % 50;
    }
}
