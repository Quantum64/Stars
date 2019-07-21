package co.q64.stars.tile;

import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.FormingBlock;
import co.q64.stars.block.RedPrimedBlock;
import co.q64.stars.tile.type.DecayEdgeTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.type.forming.RedFormingBlockType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class DecayEdgeTile extends SyncTileEntity implements ITickableTileEntity {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final long SALT = 0xabcd0123dcba3210L;

    protected @Inject FormingBlockTypes types;
    protected @Inject DecayEdgeBlock decayEdgeBlock;
    protected @Inject DecayingBlock decayingBlock;
    protected @Inject DecayBlock decayBlock;
    protected @Inject AirDecayEdgeBlock airDecayEdgeBlock;
    protected @Inject RedFormingBlockType redFormingBlockType;

    private Map<Direction, Integer> decayAmount = new HashMap<>();

    protected boolean replaceWithStaticBlock = true;
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
            int counts = 0;
            for (Direction direction : DIRECTIONS) {
                BlockPos target = getPos().offset(direction);
                Block block = world.getBlockState(target).getBlock();
                if (block instanceof DarkAirBlock) {
                    ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.01);
                    world.setBlockState(target, airDecayEdgeBlock.getDefaultState());
                }
                if (block instanceof FormedBlock) {
                    FormingBlockType type = types.get(block);
                    world.setBlockState(target, decayingBlock.getDefaultState());
                    DecayingTile decayingTile = (DecayingTile) world.getTileEntity(target);
                    decayingTile.setFormingBlockType(type);
                    decayingTile.setPrimed(block instanceof RedPrimedBlock);
                    decayingTile.setExpectedDecayTime(type.getDecayTime(target.toLong() ^ SALT) * 50);
                    decayingTile.setCalculated(true);
                    if (decayingTile == null) {
                        System.out.println("decaying tile null");
                        continue;
                    }
                    counts++;
                } else if (block instanceof DecayingBlock) {
                    int decay = decayAmount.getOrDefault(direction, 0);
                    DecayingTile decayingTile = (DecayingTile) world.getTileEntity(target);
                    if (decayingTile == null) {
                        System.out.println("decaying tile null");
                        continue;
                    }
                    FormingBlockType type = decayingTile.getFormingBlockType();
                    if (decay > type.getDecayTime(Math.abs(target.toLong() ^ SALT))) {
                        ((ServerWorld) world).spawnParticle(ParticleTypes.LARGE_SMOKE, target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.01);
                        if (decayingTile.isPrimed()) {
                            redFormingBlockType.explode((ServerWorld) world, target, true);
                        } else {
                            world.setBlockState(target, decayEdgeBlock.getDefaultState());
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
                world.setBlockState(getPos(), getDecayState(decayBlock));
            }
        }
        ticks++;
    }

    protected BlockState getDecayState(DecayBlock block) {
        return block.getDefaultState();
    }

    public IModelData getModelData() {
        return new ModelDataMap.Builder().build();
    }
}
