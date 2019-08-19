package co.q64.stars.type.forming;

import co.q64.stars.block.WhiteFormedBlock;
import co.q64.stars.item.WhiteSeedItem;
import co.q64.stars.item.WhiteSeedItem.WhiteSeedItemRobust;
import co.q64.stars.qualifier.SoundQualifiers.White;
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
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Singleton
public class WhiteFormingBlockType implements FormingBlockType {
    private final @Getter int id = 11;
    private final @Getter String name = "white";
    private final @Getter int buildTime = 1500;
    private final @Getter int buildTimeOffset = 200;
    private final @Getter float r = 226, g = 226, b = 207;

    protected @Getter @Inject WhiteFormedBlock formedBlock;
    protected @Getter @Inject Provider<WhiteSeedItem> itemProvider;
    protected @Getter @Inject Provider<WhiteSeedItemRobust> itemProviderRobust;
    protected @Getter @Inject @White Set<SoundEvent> sounds;

    protected @Inject WhiteFormingBlockType() {}

    public int getIterations(long seed) {
        return 1;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        return hasBlock(world, position, Direction.UP) ? null : Direction.UP;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        return Collections.emptyList();
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
