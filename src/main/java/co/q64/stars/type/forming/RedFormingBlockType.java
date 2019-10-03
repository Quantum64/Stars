package co.q64.stars.type.forming;

import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayEdgeBlock.DecayEdgeBlockSolid;
import co.q64.stars.block.GatewayBlock;
import co.q64.stars.block.GreenFruitBlock;
import co.q64.stars.block.GreyFormedBlock;
import co.q64.stars.block.RedFormedBlock;
import co.q64.stars.block.RedFormedBlock.RedFormedBlockHard;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.block.RedPrimedBlock.RedPrimedBlockHard;
import co.q64.stars.dimension.fleeting.FleetingDimension;
import co.q64.stars.dimension.fleeting.FleetingSolidDimension;
import co.q64.stars.dimension.hub.HubDimension;
import co.q64.stars.item.RedSeedItem;
import co.q64.stars.item.RedSeedItem.RedSeedItemRobust;
import co.q64.stars.level.LevelType;
import co.q64.stars.qualifier.SoundQualifiers.Explode;
import co.q64.stars.qualifier.SoundQualifiers.ExplodeDark;
import co.q64.stars.qualifier.SoundQualifiers.Red;
import co.q64.stars.qualifier.SoundQualifiers.Ticking;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.util.Capabilities;
import co.q64.stars.util.DecayManager;
import co.q64.stars.util.FleetingManager;
import co.q64.stars.util.Sounds;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SStopSoundPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class RedFormingBlockType implements FormingBlockType {
    private static final Direction[] DIRECTIONS = Direction.values();

    private final @Getter int id = 5;
    private final @Getter String name = "red";
    private final @Getter int buildTime = 3000;
    private final @Getter int buildTimeOffset = 0;
    private final @Getter float r = 221, g = 32, b = 32;

    protected @Getter @Inject RedPrimedBlock formedBlock;
    protected @Getter @Inject RedPrimedBlockHard formedBlockHard;
    protected @Getter @Inject Provider<RedSeedItem> itemProvider;
    protected @Getter @Inject Provider<RedSeedItemRobust> itemProviderRobust;
    protected @Getter @Inject @Red Set<SoundEvent> sounds;

    protected @Inject Provider<FleetingManager> entryManager;
    protected @Inject DecayEdgeBlock decayBlock;
    protected @Inject DecayEdgeBlockSolid decayEdgeBlockSolid;
    protected @Inject RedFormedBlock redBlock;
    protected @Inject RedFormedBlockHard redBlockHard;
    protected @Inject DecayManager decayManager;
    protected @Inject @Explode Set<SoundEvent> explodeSounds;
    protected @Inject @ExplodeDark SoundEvent explodeDarkSound;
    protected @Inject @Ticking SoundEvent tickingSound;
    protected @Inject Sounds soundManager;
    protected @Inject Capabilities capabilities;

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
        AtomicBoolean hard = new AtomicBoolean(false);
        Optional.ofNullable(world.getClosestPlayer(pos.getX(), pos.getZ(), 1000)).ifPresent(player -> {
            capabilities.gardener(player, gardener -> {
                hard.set(gardener.getLevelType() == LevelType.PURPLE);
            });
        });
        Block block = decay ? (world.getDimension() instanceof FleetingSolidDimension ? decayEdgeBlockSolid : decayBlock) : hard.get() ? redBlockHard : redBlock;
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
                    BlockState state = world.getBlockState(target);
                    if (!decayManager.isDecayBlock(world, target) && !(state.getBlock() instanceof GreyFormedBlock || state.getBlock() instanceof GatewayBlock || block instanceof GreenFruitBlock)) {
                        if (world.getDimension() instanceof HubDimension) {
                            boolean found = false;
                            for (int offset = 0; offset < 8; offset++) {
                                BlockPos test = target.offset(Direction.DOWN, offset);
                                if (world.getBlockState(test).getBlock() instanceof GatewayBlock) {
                                    found = true;
                                    break;
                                }
                            }
                            if (found) {
                                continue;
                            }
                        }
                        world.setBlockState(target, block.getDefaultState());
                        for (Direction direction : DIRECTIONS) {
                            decayManager.activateDecay(world, target.offset(direction));
                        }
                    }
                }
            }
        }
        for (ServerPlayerEntity player : world.getPlayers()) {
            if (player.getPosition().distanceSq(pos) < 200 * 200) {
                player.connection.sendPacket(new SStopSoundPacket(tickingSound.getRegistryName(), SoundCategory.MASTER));
            }
            if (player.getPosition().distanceSq(pos) < 2.9 * 2.9) {
                if (player.getEntityWorld().getDimension() instanceof FleetingDimension) {
                    entryManager.get().createDarkness(player);
                } else if (player.getEntityWorld().getDimension() instanceof HubDimension) {
                    capabilities.gardener(player, gardener -> {
                        BlockPos to = gardener.getHubSpawn();
                        player.teleport(player.getServerWorld(), to.getX(), to.getY(), to.getZ(), player.rotationYaw, player.rotationPitch);
                    });
                }
            }
        }
    }
}
