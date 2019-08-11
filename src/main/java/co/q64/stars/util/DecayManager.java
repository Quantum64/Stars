package co.q64.stars.util;

import co.q64.stars.block.AirDecayBlock;
import co.q64.stars.block.AirDecayEdgeBlock;
import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.DecayEdgeBlock;
import co.q64.stars.block.DecayEdgeBlock.DecayEdgeBlockSolid;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.entity.PickupEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DecayManager {
    private static final Direction[] DIRECTIONS = Direction.values();

    protected @Inject DecayBlock decayBlock;
    protected @Inject AirDecayEdgeBlock airDecayEdgeBlock;
    protected @Inject DecayEdgeBlock decayEdgeBlock;
    protected @Inject DecayEdgeBlockSolid decayEdgeBlockSolid;
    protected @Inject SpecialDecayEdgeBlock specialDecayEdgeBlock;
    protected @Inject EntityType<PickupEntity> pickupEntityType;

    protected @Inject DecayManager() {}

    public void activateDecay(ServerWorld world, BlockPos pos) {
        for (Direction direction : DIRECTIONS) {
            BlockPos target = pos.offset(direction);
            BlockState state = world.getBlockState(target);
            if (state.getBlock() instanceof AirDecayBlock) {
                world.setBlockState(target, airDecayEdgeBlock.getDefaultState());
            } else if (state.getBlock() instanceof SpecialDecayBlock) {
                world.setBlockState(target, specialDecayEdgeBlock.getDefaultState().with(SpecialDecayBlock.TYPE, state.get(SpecialDecayBlock.TYPE)));
            } else if (state.getBlock() instanceof DecayEdgeBlockSolid) {
                world.setBlockState(target, decayEdgeBlockSolid.getDefaultState());
            } else if (state.getBlock() instanceof DecayBlock) {
                world.setBlockState(target, decayEdgeBlock.getDefaultState());
            }
        }
    }

    public boolean isDecayBlock(ServerWorld world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return block == decayBlock || block instanceof DecayEdgeBlock || block instanceof SpecialDecayBlock || block instanceof SpecialDecayEdgeBlock;
    }

    public void createSpecialDecay(World world, BlockPos pos, SpecialDecayType type) {
        createSpecialDecay(world, pos, type, true);
    }

    public void createSpecialDecay(IWorld world, BlockPos pos, SpecialDecayType type, boolean notify) {
        world.setBlockState(pos, specialDecayEdgeBlock.getDefaultState().with(SpecialDecayBlock.TYPE, type), notify ? 3 : 2);
    }

    public static enum SpecialDecayType implements IStringSerializable {
        HEART, DOOR, CHALLENGE_DOOR, KEY;

        public String getName() {
            return name().toLowerCase();
        }
    }
}
