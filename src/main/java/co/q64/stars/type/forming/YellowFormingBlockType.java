package co.q64.stars.type.forming;

import co.q64.stars.block.YellowFormedBlock;
import co.q64.stars.block.YellowFormedBlock.YellowFormedBlockHard;
import co.q64.stars.item.YellowSeedItem;
import co.q64.stars.item.YellowSeedItem.YellowSeedItemRobust;
import co.q64.stars.qualifier.SoundQualifiers.Yellow;
import co.q64.stars.type.FormingBlockType;
import lombok.Getter;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Singleton
public class YellowFormingBlockType implements FormingBlockType {
    private final @Getter int id = 0;
    private final @Getter String name = "yellow";
    private final @Getter int buildTime = 150;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 255, g = 234, b = 0;

    protected @Getter @Inject YellowFormedBlockHard formedBlockHard;
    protected @Getter @Inject YellowFormedBlock formedBlock;
    protected @Getter @Inject Provider<YellowSeedItem> itemProvider;
    protected @Getter @Inject Provider<YellowSeedItemRobust> itemProviderRobust;
    protected @Getter @Inject @Yellow Set<SoundEvent> sounds;

    protected @Inject YellowFormingBlockType() {}

    public int getIterations(long seed) {
        return (int) (8 + (seed % 3));
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        Direction result = null;
        if (!hasBlock(world, position, Direction.UP)) {
            result = Direction.UP;
        }
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
        return 30 + (int) seed % 10;
    }
}
