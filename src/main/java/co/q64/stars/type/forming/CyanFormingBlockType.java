package co.q64.stars.type.forming;

import co.q64.stars.block.CyanFormedBlock;
import co.q64.stars.item.BlueSeedItem;
import co.q64.stars.item.CyanSeedItem;
import co.q64.stars.qualifier.SoundQualifiers.Cyan;
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
public class CyanFormingBlockType implements FormingBlockType {
    private final @Getter int id = 4;
    private final @Getter String name = "cyan";
    private final @Getter int buildTime = 1000;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 167, g = 255, b = 250;

    protected @Getter @Inject CyanFormedBlock formedBlock;
    protected @Getter @Inject Provider<CyanSeedItem> itemProvider;
    protected @Getter @Inject @Cyan Set<SoundEvent> sounds;

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
