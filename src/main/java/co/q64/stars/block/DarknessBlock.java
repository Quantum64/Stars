package co.q64.stars.block;

import co.q64.stars.state.DarknessState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DarknessBlock extends BaseBlock {
    protected DarknessBlock(String id, Properties settings) {
        super(id, settings);
        setDefaultState(getDefaultState()
                .with(DarknessState.CONNECT_DOWN, false)
                .with(DarknessState.CONNECT_UP, false)
                .with(DarknessState.CONNECT_WEST, false)
                .with(DarknessState.CONNECT_EAST, false)
                .with(DarknessState.CONNECT_NORTH, false)
                .with(DarknessState.CONNECT_SOUTH, false));
    }

    protected @Inject DarknessBlock() {
        this("darkness", Properties.create(Material.GLASS).hardnessAndResistance(-1f, 3600000f));
    }

    protected void fillStateContainer(Builder<Block, BlockState> state) {
        super.fillStateContainer(state
                .add(DarknessState.CONNECT_DOWN)
                .add(DarknessState.CONNECT_UP)
                .add(DarknessState.CONNECT_WEST)
                .add(DarknessState.CONNECT_EAST)
                .add(DarknessState.CONNECT_NORTH)
                .add(DarknessState.CONNECT_SOUTH));
    }

    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
        state = state.with(DarknessState.get(facing), facingState.getBlock() instanceof DarknessBlock);
        return super.updatePostPlacement(state, facing, facingState, world, pos, facingPos);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
