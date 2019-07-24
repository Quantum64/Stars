package co.q64.stars.type.forming;

import co.q64.stars.block.PinkFormedBlock;
import co.q64.stars.item.PinkSeedItem;
import co.q64.stars.qualifier.SoundQualifiers.Pink;
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
public class PinkFormingBlockType implements FormingBlockType {
    private final @Getter int id = 2;
    private final @Getter String name = "pink";
    private final @Getter int buildTime = 15000;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 228, g = 0, b = 255;

    protected @Getter @Inject PinkFormedBlock formedBlock;
    protected @Getter @Inject Provider<PinkSeedItem> itemProvider;
    protected @Getter @Inject @Pink Set<SoundEvent> sounds;

    protected @Inject PinkFormingBlockType() {}

    public int getIterations(long seed) {
        return 256;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        if (!hasBlock(world, position, Direction.UP)) {
            return Direction.UP;
        }
        return null;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        if (!hasBlock(world, position, Direction.UP)) {
            return Collections.singletonList(Direction.UP);
        }
        return Collections.emptyList();
    }

    public int getDecayTime(long seed) {
        return 10;
    }
}
