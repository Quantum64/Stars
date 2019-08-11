package co.q64.stars.tile;

import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayBlock.DecayBlockSolid;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayEdgeBlock.DecayEdgeBlockSolid;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.DecayingBlock.DecayingBlockHard;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.GreenFruitBlock;
import co.q64.stars.block.HardBlock;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.block.SeedBlock;
import co.q64.stars.dimension.fleeting.FleetingSolidDimension;
import co.q64.stars.level.LevelType;
import co.q64.stars.qualifier.SoundQualifiers.Dark;
import co.q64.stars.qualifier.SoundQualifiers.DarkAir;
import co.q64.stars.tile.type.DecayEdgeTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.type.forming.RedFormingBlockType;
import co.q64.stars.util.Capabilities;
import co.q64.stars.util.DecayManager;
import co.q64.stars.util.DecayManager.SpecialDecayType;
import co.q64.stars.util.Sounds;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class DecayEdgeTile extends SyncTileEntity implements ITickableTileEntity {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final long SALT = 0xabcd0123dcba3210L;

    protected @Inject FormingBlockTypes types;
    protected @Inject DecayEdgeBlock decayEdgeBlock;
    protected @Inject DecayEdgeBlockSolid decayEdgeBlockSolid;
    protected @Inject DecayingBlock decayingBlock;
    protected @Inject DecayingBlockHard decayingBlockHard;
    protected @Inject DecayBlock decayBlock;
    protected @Inject DecayBlock trueDecayBlock;
    protected @Inject DecayBlockSolid decayBlockSolid;
    protected @Inject AirDecayEdgeBlock airDecayEdgeBlock;
    protected @Inject RedFormingBlockType redFormingBlockType;
    protected @Inject DecayManager decayManager;
    protected @Inject Sounds sounds;
    protected @Inject @Dark Set<SoundEvent> darkSounds;
    protected @Inject @DarkAir SoundEvent darkAir;
    protected @Inject Capabilities capabilities; // TODO remove

    private Map<Direction, Integer> decayAmount = new HashMap<>();

    protected boolean replaceWithStaticBlock = true;
    private @Setter double multiplier = 1;
    private int ticks = 0;

    @Inject
    protected DecayEdgeTile(DecayEdgeTileType type) {
        super(type);
    }

    protected DecayEdgeTile(TileEntityType<?> type) {
        super(type);
    }

    public void tick() {
        if (!world.isRemote) {
            if (ticks == 0) {
                // Temporary hack
                PlayerEntity closest = ((ServerWorld) world).getClosestPlayer(pos.getX(), pos.getZ(), 1000);
                if (closest != null) {
                    capabilities.gardener(closest, gardener -> {
                        if (gardener.getLevelType() == LevelType.WHITE) {
                            multiplier = 1.25;
                        } else if (gardener.getLevelType() == LevelType.ORANGE) {
                            multiplier = 0.5;
                        } else if (gardener.getLevelType() == LevelType.CYAN) {
                            multiplier = 0.2;
                        }
                    });
                }
            }
            int counts = 0;
            for (Direction direction : DIRECTIONS) {
                BlockPos target = getPos().offset(direction);
                Block block = world.getBlockState(target).getBlock();
                if (block instanceof DarkAirBlock) {
                    ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.01);
                    world.setBlockState(target, airDecayEdgeBlock.getDefaultState());
                    if (!(this instanceof AirDecayEdgeTile)) {
                        sounds.playSound((ServerWorld) world, pos, Collections.singleton(darkAir), 2f, 0.8f + (ThreadLocalRandom.current().nextFloat() * 0.4f));
                    }
                }
                if (block instanceof FormedBlock) {
                    FormingBlockType type = types.get(block);
                    world.setBlockState(target, block instanceof HardBlock ? decayingBlockHard.getDefaultState() : decayingBlock.getDefaultState());
                    Optional.ofNullable((DecayingTile) world.getTileEntity(target)).ifPresent(decayingTile -> {
                        decayingTile.setFormingBlockType(type);
                        decayingTile.setPrimed(block instanceof RedPrimedBlock);
                        decayingTile.setFruit(block instanceof GreenFruitBlock);
                        decayingTile.setExpectedDecayTime(getDecayTicks(type, target) * 50);
                        decayingTile.setCalculated(true);
                    });
                    counts++;
                } else if (block instanceof SeedBlock) {
                    Optional.ofNullable((SeedTile) world.getTileEntity(target)).ifPresent(seedTile -> {
                        world.setBlockState(target, block instanceof HardBlock ? decayingBlockHard.getDefaultState() : decayingBlock.getDefaultState());
                        Optional.ofNullable((DecayingTile) world.getTileEntity(target)).ifPresent(decayingTile -> {
                            decayingTile.setFormingBlockType(seedTile.getFormingBlockType());
                            decayingTile.setPrimed(seedTile.isPrimed());
                            decayingTile.setFruit(seedTile.isFruit());
                            decayingTile.setGrowTicks(seedTile.getGrowTicks());
                            decayingTile.setSeedType(seedTile.getSeedType());
                            decayingTile.setHasSeed(true);
                            decayingTile.setExpectedDecayTime(getDecayTicks(seedTile.getFormingBlockType(), target) * 50);
                            decayingTile.setCalculated(true);
                        });
                    });
                    counts++;
                } else if (block instanceof DecayingBlock) {
                    int decay = decayAmount.getOrDefault(direction, 0);
                    DecayingTile decayingTile = (DecayingTile) world.getTileEntity(target);
                    if (decayingTile == null) {
                        System.out.println("decaying tile null");
                        continue;
                    }
                    FormingBlockType type = decayingTile.getFormingBlockType();
                    if (decay > getDecayTicks(type, target)) {
                        ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.01);
                        sounds.playRangedSound((ServerWorld) world, target, darkSounds, 10, 0.5f);
                        if (decayingTile.isPrimed()) {
                            redFormingBlockType.explode((ServerWorld) world, target, true);
                        } else if (decayingTile.isFruit()) {
                            decayManager.createSpecialDecay(world, target, SpecialDecayType.HEART);
                        } else {
                            world.setBlockState(target, world.getDimension() instanceof FleetingSolidDimension ? decayEdgeBlockSolid.getDefaultState() : decayEdgeBlock.getDefaultState());
                            Optional.ofNullable((DecayEdgeTile) world.getTileEntity(target)).ifPresent(tile -> {
                                tile.setMultiplier(multiplier);
                            });
                        }
                    } else {
                        //decayAmount.put(direction, decay + 10);
                        decayAmount.put(direction, decay + 1);
                        counts++;
                    }
                } else if (block instanceof FormingBlock) {
                    world.setBlockState(target, Blocks.AIR.getDefaultState());
                }
            }
            if (counts == 0) {
                if (decayBlock == trueDecayBlock && world.getDimension() instanceof FleetingSolidDimension) {
                    world.setBlockState(getPos(), getDecayState(decayBlockSolid));
                } else {
                    world.setBlockState(getPos(), getDecayState(decayBlock));
                }
            }
            ticks++;
        }
    }

    private int getDecayTicks(FormingBlockType type, BlockPos pos) {
        return (int) (type.getDecayTime(Math.abs(pos.toLong() ^ SALT)) * multiplier);
    }

    protected BlockState getDecayState(DecayBlock block) {
        return block.getDefaultState();
    }

    public IModelData getModelData() {
        return new ModelDataMap.Builder().build();
    }
}
