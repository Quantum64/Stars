package co.q64.stars.type.forming;

import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.RedFormedBlock;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.item.RedSeedItem;
import co.q64.stars.qualifier.SoundQualifiers.Explode;
import co.q64.stars.qualifier.SoundQualifiers.ExplodeDark;
import co.q64.stars.qualifier.SoundQualifiers.Red;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.util.DecayManager;
import co.q64.stars.util.FleetingManager;
import co.q64.stars.util.Sounds;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Singleton
public class RedFormingBlockType implements FormingBlockType {
    private final @Getter int id = 5;
    private final @Getter String name = "red";
    private final @Getter int buildTime = 3000;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 255, g = 0, b = 0;

    protected @Getter @Inject RedPrimedBlock formedBlock;
    protected @Getter @Inject Provider<RedSeedItem> itemProvider;
    protected @Getter @Inject @Red Set<SoundEvent> sounds;

    protected @Inject Provider<FleetingManager> entryManager;
    protected @Inject DecayEdgeBlock decayBlock;
    protected @Inject RedFormedBlock redBlock;
    protected @Inject DecayManager decayManager;
    protected @Inject @Explode Set<SoundEvent> explodeSounds;
    protected @Inject @ExplodeDark SoundEvent explodeDarkSound;
    protected @Inject Sounds soundManager;

    protected @Inject RedFormingBlockType() {}

    public int getIterations(long seed) {
        return 0;
    }

    public Direction getInitialDirection(World world, BlockPos position) {
        if (!hasBlock(world, position, Direction.UP)) {
            return Direction.UP;
        }
        return null;
    }

    public List<Direction> getNextDirections(World world, BlockPos position, Direction last, int iterations) {
        return Collections.emptyList();
    }

    public int getDecayTime(long seed) {
        return 50 + (int) seed % 10;
    }

    public void explode(ServerWorld world, BlockPos pos, boolean decay) {
        Block block = decay ? decayBlock : redBlock;
        if (decay) {
            soundManager.playSound(world, pos, explodeDarkSound, 4f);
        } else {
            soundManager.playSound(world, pos, explodeSounds, 2f);
        }
        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    boolean az = Math.abs(z) == 3, ax = Math.abs(x) == 3, ay = Math.abs(y) == 3;
                    if (ax && (ay || az) || (ay && az)) {
                        continue;
                    }
                    BlockPos target = pos.add(x, y, z);
                    if (!decayManager.isDecayBlock(world, target)) {
                        world.setBlockState(target, block.getDefaultState());
                    }
                }
            }
        }
        for (ServerPlayerEntity player : world.getPlayers()) {
            if (player.getPosition().distanceSq(pos) < 2.9 * 2.9) {
                entryManager.get().createDarkness(player);
            }
        }
    }
}
