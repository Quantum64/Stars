package co.q64.stars.tile;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DarkAirBlock;
import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.DarknessEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayingBlock;
import co.q64.stars.block.DoorBlock;
import co.q64.stars.block.FormedBlock;
import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.tile.SpecialDecayEdgeTile.SpecialDecayType;
import co.q64.stars.tile.type.DarknessEdgeTileType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.inject.Inject;

public class DarknessEdgeTile extends TileEntity implements ITickableTileEntity {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final long SALT = 0xabcd0123dcba3210L;

    protected @Inject DarknessEdgeBlock darknessEdgeBlock;
    protected @Inject DarknessBlock darknessBlock;
    protected @Inject SpecialAirBlock specialAirBlock;
    protected @Inject DoorBlock doorBlock;

    @Inject
    protected DarknessEdgeTile(DarknessEdgeTileType type) {
        super(type);
    }

    public void tick() {
        if (!world.isRemote) {
            for (Direction direction : DIRECTIONS) {
                BlockPos target = getPos().offset(direction);
                Block block = world.getBlockState(target).getBlock();
                if (block == Blocks.AIR || block instanceof DarkAirBlock || block instanceof AirDecayBlock || block instanceof AirDecayEdgeBlock) {
                    world.setBlockState(target, darknessBlock.getDefaultState());
                } else if (block instanceof SpecialDecayEdgeBlock || block instanceof SpecialDecayBlock) {
                    SpecialDecayType type = SpecialDecayType.HEART;
                    if (block instanceof SpecialDecayBlock) {
                        type = world.getBlockState(target).get(SpecialDecayBlock.TYPE);
                    } else {
                        SpecialDecayEdgeTile tile = (SpecialDecayEdgeTile) world.getTileEntity(target);
                        if (tile != null) {
                            type = tile.getDecayType();
                        }
                    }
                    switch (type) {
                        case HEART:
                            break;
                        case DOOR:
                            world.setBlockState(target, doorBlock.getDefaultState());
                            break;
                        case CHALLENGE_DOOR:
                            break;
                    }
                } else if (block instanceof FormedBlock || block instanceof DecayBlock || block instanceof DecayEdgeBlock || block instanceof DecayingBlock) {
                    world.setBlockState(target, darknessEdgeBlock.getDefaultState());
                }
            }
            world.setBlockState(getPos(), specialAirBlock.getDefaultState());
        }
    }
}
