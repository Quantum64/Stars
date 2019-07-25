package co.q64.stars.type.forming;

import co.q64.stars.block.GreenFormedBlock;
import co.q64.stars.item.GreenSeedItem;
import co.q64.stars.qualifier.SoundQualifiers.Green;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class GreenFormingBlockType implements FormingBlockType {
    private final @Getter int id = 7;
    private final @Getter String name = "green";
    private final @Getter int buildTime = 250;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 48, g = 255, b = 0;

    protected @Getter @Inject GreenFormedBlock formedBlock;
    protected @Getter @Inject Provider<GreenSeedItem> itemProvider;
    protected @Getter @Inject @Green Set<SoundEvent> sounds;

    protected @Inject GreenFormingBlockType() {}

    public int getIterations(long seed) {
        return (int) (8 + (seed % 6));
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
        List<Direction> test = new ArrayList<>(Arrays.asList(Direction.values()));
        while (!test.isEmpty()) {
            //int index = Math.toIntExact(Math.abs(position.toLong() ^ 0x2837018275937103L) % test.size());
            int index = ThreadLocalRandom.current().nextInt(test.size());
            Direction selected = test.get(index);
            if (hasBlock(world, position, selected)) {
                test.remove(index);
                continue;
            }
            return Collections.singletonList(selected);
        }
        return Collections.emptyList();
    }

    public int getDecayTime(long seed) {
        return 60 + (int) seed % 10;
    }
}
