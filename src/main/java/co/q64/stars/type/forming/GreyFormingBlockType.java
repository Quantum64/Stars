package co.q64.stars.type.forming;

import co.q64.stars.block.GreyFormedBlock;
import co.q64.stars.block.OrangeFormedBlock;
import co.q64.stars.item.YellowSeedItem;
import co.q64.stars.qualifier.SoundQualifiers.Dark;
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
public class GreyFormingBlockType implements FormingBlockType {
    private final @Getter int id = 9;
    private final @Getter String name = "grey";
    private final @Getter int buildTime = 0;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 0, g = 0, b = 0;

    protected @Getter @Inject GreyFormedBlock formedBlock;
    protected @Getter @Inject Provider<YellowSeedItem> itemProvider;
    protected @Getter @Inject @Dark Set<SoundEvent> sounds;

    protected @Inject GreyFormingBlockType() {}

    public int getIterations(long seed) {
        return 0;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        return null;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        return Collections.emptyList();
    }

    public int getDecayTime(long seed) {
        return 150 + (int) seed % 50;
    }

    public boolean canGrow() {
        return false;
    }
}