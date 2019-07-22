package co.q64.stars.tile;

import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.GreenFruitBlock;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.tile.type.FormingTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.type.forming.GreenFormingBlockType;
import co.q64.stars.type.forming.RedFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import co.q64.stars.util.DecayManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SPlayerPositionLookPacket.Flags;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class FormingTile extends SyncTileEntity implements ITickableTileEntity {
    private static final int FRUIT_CHANCE = 4;
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final long SALT = 0x1029adbc3847efefL;

    protected @Inject FormingBlockTypes types;
    protected @Inject FormingBlock formingBlock;
    protected @Inject DecayEdgeBlock decayEdgeBlock;
    protected @Inject AirDecayEdgeBlock airDecayEdgeBlock;
    protected @Inject RedFormingBlockType redFormingBlockType;
    protected @Inject GreenFormingBlockType greenFormingBlockType;
    protected @Inject GreenFruitBlock greenFruitBlock;
    protected @Inject DecayManager decayManager;

    private @Setter @Getter boolean calculated = false;
    private @Setter @Getter boolean first = true;
    private @Setter @Getter int iterationsRemaining = 0;
    private @Setter @Getter Direction direction = Direction.UP;
    private @Setter @Getter FormingBlockType formType;
    private @Getter long placed = System.currentTimeMillis();
    private @Getter int buildTime = 0;

    private int ticks = 0;
    private int formTicks;

    @Inject
    protected FormingTile(FormingTileType type) {
        super(type);
    }

    @Inject
    protected void setupDefault(YellowFormingBlockType type) {
        setup(type);
    }

    public void setup(FormingBlockType type) {
        this.formType = type;
        this.buildTime = formType.getBuildTime();
        if (formType.getBuildTimeOffset() > 0) {
            buildTime += ThreadLocalRandom.current().nextInt(formType.getBuildTimeOffset() * 2) - formType.getBuildTimeOffset();
        }
        this.formTicks = buildTime / 50; // 50 ms per tick
        if (first) {
            this.iterationsRemaining = formType.getIterations(getSeed());
        }
    }

    public void read(CompoundNBT compound) {
        direction = Direction.valueOf(compound.getString("direction"));
        formType = types.get(compound.getInt("formType"));
        iterationsRemaining = compound.getInt("iterationsRemaining");
        placed = compound.getLong("placed");
        calculated = compound.getBoolean("calculated");
        buildTime = compound.getInt("buildTime");
        super.read(compound);
    }

    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT result = super.write(compound);
        result.putString("direction", direction.name());
        result.putInt("formType", formType.getId());
        result.putInt("iterationsRemaining", iterationsRemaining);
        result.putLong("placed", placed);
        result.putBoolean("calculated", calculated);
        result.putInt("buildTime", buildTime);
        return result;
    }

    private long getSeed() {
        return Math.abs(getPos().toLong() ^ SALT);
    }

    public void tick() {
        if (!world.isRemote) {
            if (first && !calculated) {
                direction = formType.getInitialDirection(world, pos);
                if (direction == null) {
                    direction = Direction.UP;
                }
                calculated = true;
                world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
            }
            if (ticks == formTicks) {
                ((ServerWorld) world).spawnParticle(ParticleTypes.CLOUD, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, 20, 0.4, 0.4, 0.4, 0.01);
                if (formType == greenFormingBlockType && ThreadLocalRandom.current().nextInt(FRUIT_CHANCE) == 0) {
                    world.setBlockState(getPos(), greenFruitBlock.getDefaultState());
                } else {
                    world.setBlockState(getPos(), formType.getFormedBlock().getDefaultState());
                }

                // Check for primed block
                BlockPos primed = getPos().offset(direction.getOpposite());
                boolean explode = world.getBlockState(primed).getBlock() instanceof RedPrimedBlock;
                if (!explode) {
                    if (world.getBlockState(primed).getBlock() instanceof DecayingBlock) {
                        DecayingTile tile = (DecayingTile) world.getTileEntity(primed);
                        if (tile != null) {
                            explode = tile.isPrimed();
                        }
                    }
                }
                if (explode) {
                    redFormingBlockType.explode((ServerWorld) world, primed, false);
                } else {
                    // Move nearby
                    if (direction == Direction.UP) {
                        for (ServerPlayerEntity player : ((ServerWorld) world).getPlayers()) {
                            if (player.posX > getPos().getX() - 0.3 && player.posX < getPos().getX() + 1.3
                                    && player.posZ > getPos().getZ() - 0.3 && player.posZ < getPos().getZ() + 1.3
                                    && player.posY > getPos().getY() - 0.01 && player.posY < getPos().getY() + 1.01) {
                                if (world.getBlockState(getPos().offset(Direction.UP)).getBlock() instanceof FormedBlock) {
                                    world.setBlockState(getPos(), decayEdgeBlock.getDefaultState());
                                    player.teleport((ServerWorld) world, getPos().getX() + 0.5, getPos().getY() + 0.1, getPos().getZ() + 0.5, player.rotationYaw, player.rotationPitch);
                                } else {
                                    player.connection.setPlayerLocation(player.posX, player.posY + 1, player.posZ, player.rotationYaw, player.rotationPitch, Arrays.asList(Flags.X, Flags.Y, Flags.Z, Flags.X_ROT, Flags.Y_ROT).stream().collect(Collectors.toSet()));
                                }
                            }
                        }
                    }

                    decayManager.activateDecay((ServerWorld) world, pos);

                    if (iterationsRemaining > 0) {
                        for (Direction next : formType.getNextDirections(world, pos, direction, iterationsRemaining - 1)) {
                            BlockPos placed = getPos().add(next.getXOffset(), next.getYOffset(), next.getZOffset());
                            world.setBlockState(placed, formingBlock.getDefaultState());
                            Optional.ofNullable((FormingTile) world.getTileEntity(placed)).ifPresent(spawned -> {
                                spawned.setFirst(false);
                                spawned.setIterationsRemaining(iterationsRemaining - 1);
                                spawned.setDirection(next);
                                spawned.setup(formType);
                                spawned.setCalculated(true);
                            });
                        }
                    }
                }
            }
            ticks++;
        }
    }
}
