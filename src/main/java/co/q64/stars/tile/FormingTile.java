package co.q64.stars.tile;

import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.CyanFormedBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.GatewayBlock;
import co.q64.stars.block.GreenFruitBlock;
import co.q64.stars.block.GreenFruitBlock.GreenFruitBlockHard;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.dimension.fleeting.FleetingDimension;
import co.q64.stars.dimension.hub.HubDimension;
import co.q64.stars.level.LevelType;
import co.q64.stars.tile.type.FormingTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.type.forming.BrownFormingBlockType;
import co.q64.stars.type.forming.CyanFormingBlockType;
import co.q64.stars.type.forming.GreenFormingBlockType;
import co.q64.stars.type.forming.RedFormingBlockType;
import co.q64.stars.type.forming.YellowFormingBlockType;
import co.q64.stars.util.Capabilities;
import co.q64.stars.util.DecayManager;
import co.q64.stars.util.Sounds;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SPlayerPositionLookPacket.Flags;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FormingTile extends SyncTileEntity implements ITickableTileEntity {
    private static final int FRUIT_CHANCE = 3;
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final long SALT = 0x1029adbc3847efefL;

    protected @Inject FormingBlockTypes types;
    protected @Inject FormingBlock formingBlock;
    protected @Inject DecayEdgeBlock decayEdgeBlock;
    protected @Inject AirDecayEdgeBlock airDecayEdgeBlock;
    protected @Inject RedFormingBlockType redFormingBlockType;
    protected @Inject GreenFormingBlockType greenFormingBlockType;
    protected @Inject GreenFruitBlock greenFruitBlock;
    protected @Inject GreenFruitBlockHard greenFruitBlockHard;
    protected @Inject DecayManager decayManager;
    protected @Inject BrownFormingBlockType brownFormingBlockType;
    protected @Inject Sounds sounds;
    protected @Inject Capabilities capabilities;
    protected @Inject CyanFormedBlock cyanFormedBlock;

    private @Setter @Getter boolean calculated = false;
    private @Setter @Getter boolean first = true;
    private @Setter @Getter int iterationsRemaining = 0;
    private @Setter @Getter Direction direction = Direction.UP;
    private @Setter @Getter FormingBlockType formType;
    private @Setter @Getter double multiplier = 1;
    private @Setter @Getter boolean hard = false;
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
        if (formType == brownFormingBlockType && direction == Direction.DOWN) {
            buildTime = 250;
        }
        this.buildTime = (int) (buildTime * multiplier);
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
                sounds.playSound((ServerWorld) world, pos, formType.getSounds(), formType instanceof BrownFormingBlockType ? 0.6f : 1f);

                Consumer<Boolean> task = door -> {
                    if (formType == greenFormingBlockType && world.getDimension() instanceof FleetingDimension && ThreadLocalRandom.current().nextInt(FRUIT_CHANCE) == 0 && !door) {
                        world.setBlockState(getPos(), hard ? greenFruitBlockHard.getDefaultState() : greenFruitBlock.getDefaultState());
                    } else {
                        world.setBlockState(getPos(), hard ? formType.getFormedBlockHard().getDefaultState() : formType.getFormedBlock().getDefaultState());
                    }
                };
                PlayerEntity closest = world.getClosestPlayer(pos.getX(), pos.getZ(), 100);
                if (closest == null) {
                    task.accept(false);
                } else {
                    capabilities.gardener(closest, gardener -> task.accept(gardener.isOpenDoor()));
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
                    for (ServerPlayerEntity player : ((ServerWorld) world).getPlayers()) {
                        if (player.posX > getPos().getX() - 0.3 && player.posX < getPos().getX() + 1.3
                                && player.posZ > getPos().getZ() - 0.3 && player.posZ < getPos().getZ() + 1.3
                                && player.posY > getPos().getY() - 0.01 && player.posY < getPos().getY() + 0.5) {
                            if (world.getBlockState(getPos().offset(Direction.UP)).getBlock() instanceof FormedBlock) {
                                world.setBlockState(getPos(), decayEdgeBlock.getDefaultState());
                                player.teleport((ServerWorld) world, getPos().getX() + 0.5, getPos().getY() + 0.1, getPos().getZ() + 0.5, player.rotationYaw, player.rotationPitch);
                            } else {
                                player.connection.setPlayerLocation(player.posX, player.posY + 1, player.posZ, player.rotationYaw, player.rotationPitch, Arrays.asList(Flags.X, Flags.Y, Flags.Z, Flags.X_ROT, Flags.Y_ROT).stream().collect(Collectors.toSet()));
                            }
                        }
                    }

                    decayManager.activateDecay((ServerWorld) world, pos);
                    if (iterationsRemaining > 0) {
                        List<Direction> directions = formType.getNextDirections(world, pos, direction, iterationsRemaining - 1);
                        if (formType instanceof BrownFormingBlockType && direction == Direction.DOWN && directions.isEmpty()) {
                            directions = new ArrayList<>();
                            iterationsRemaining = 1;
                            for (Direction d : DIRECTIONS) {
                                if (d == Direction.UP) {
                                    continue;
                                }
                                directions.add(d);
                            }
                        }
                        for (Direction next : directions) {
                            BlockPos placed = getPos().add(next.getXOffset(), next.getYOffset(), next.getZOffset());
                            if (world.getDimension() instanceof HubDimension) {
                                boolean found = false;
                                for (int offset = 0; offset < 8; offset++) {
                                    BlockPos test = placed.offset(Direction.DOWN, offset);
                                    if (world.getBlockState(test).getBlock() instanceof GatewayBlock) {
                                        found = true;
                                        break;
                                    }
                                }
                                if (found) {
                                    continue;
                                }
                            }
                            world.setBlockState(placed, formingBlock.getDefaultState());
                            Optional.ofNullable((FormingTile) world.getTileEntity(placed)).ifPresent(spawned -> {
                                spawned.setFirst(false);
                                spawned.setIterationsRemaining(iterationsRemaining - 1);
                                spawned.setDirection(next);
                                spawned.setMultiplier(multiplier);
                                spawned.setHard(hard);
                                spawned.setup(formType);
                                spawned.setCalculated(true);
                            });
                        }
                        if (world.getDimension() instanceof FleetingDimension) {
                            if (formType instanceof GreenFormingBlockType) {
                                capabilities.gardener(closest, gardener -> {
                                    if (gardener.getLevelType() == LevelType.GREEN && ThreadLocalRandom.current().nextInt(3) == 0) {
                                        List<Direction> options = new ArrayList<>(Arrays.asList(DIRECTIONS));
                                        Collections.shuffle(options);
                                        for (Direction option : options) {
                                            BlockPos test = pos.offset(option);
                                            BlockState state = world.getBlockState(test);
                                            Block block = state.getBlock();
                                            if (block.isAir(state, world, test) || block instanceof DarkAirBlock || block instanceof SpecialAirBlock) {
                                                world.setBlockState(test, formingBlock.getDefaultState());
                                                Optional.ofNullable((FormingTile) world.getTileEntity(test)).ifPresent(spawned -> {
                                                    spawned.setFirst(false);
                                                    spawned.setIterationsRemaining(iterationsRemaining + (ThreadLocalRandom.current().nextBoolean() ? 1 : 0));
                                                    spawned.setDirection(option);
                                                    spawned.setMultiplier(multiplier);
                                                    spawned.setHard(hard);
                                                    spawned.setup(formType);
                                                    spawned.setCalculated(true);
                                                });
                                                break;
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    } else if (iterationsRemaining == 0 && world.getDimension() instanceof FleetingDimension) {
                        if (formType instanceof CyanFormingBlockType) {
                            capabilities.gardener(closest, gardener -> {
                                if (gardener.getLevelType() == LevelType.CYAN) {
                                    int remaining = 300;
                                    Queue<BlockPos> check = new ArrayDeque<>();
                                    check.add(pos);
                                    while (!check.isEmpty()) {
                                        remaining--;
                                        if (remaining < 0) {
                                            break;
                                        }
                                        BlockPos target = check.poll();
                                        for (Direction direction : DIRECTIONS) {
                                            BlockPos update = target.offset(direction);
                                            if (world.getBlockState(update).getBlock() instanceof FormedBlock && !(world.getBlockState(update).getBlock() instanceof CyanFormedBlock)) {
                                                world.setBlockState(update, cyanFormedBlock.getDefaultState(), 3);
                                                check.add(update);
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
            ticks++;
        }
    }
}
