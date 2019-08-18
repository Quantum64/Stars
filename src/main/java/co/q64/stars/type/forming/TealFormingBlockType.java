package co.q64.stars.type.forming;

import co.q64.stars.block.TealFormedBlock;
import co.q64.stars.item.TealSeedItem;
import co.q64.stars.item.TealSeedItem.TealSeedItemRobust;
import co.q64.stars.item.YellowSeedItem.YellowSeedItemRobust;
import co.q64.stars.qualifier.SoundQualifiers.Teal;
import co.q64.stars.type.FormingBlockType;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class TealFormingBlockType implements FormingBlockType {
    private final @Getter int id = 10;
    private final @Getter String name = "teal";
    private final @Getter int buildTime = 1000;
    private final @Getter int buildTimeOffset = 200;
    private final @Getter float r = 43, g = 207, b = 127;

    protected @Getter @Inject TealFormedBlock formedBlock;
    protected @Getter @Inject Provider<TealSeedItem> itemProvider;
    protected @Getter @Inject Provider<TealSeedItemRobust> itemProviderRobust;
    protected @Getter @Inject @Teal Set<SoundEvent> sounds;

    protected @Inject TealFormingBlockType() {}

    public int getIterations(long seed) {
        return 1;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        return hasBlock(world, position, Direction.UP) ? null : Direction.UP;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        return Arrays.stream(Direction.values()).filter(direction -> !hasBlock(world, position, direction)).collect(Collectors.toList());
    }

    public Block getFormedBlockHard() {
        return formedBlock;
    }

    public int getDecayTime(long seed) {
        return 150 + (int) seed % 50;
    }

    public boolean canGrow() {
        return false;
    }
}
