package co.q64.stars.block;

import co.q64.stars.state.BlockStates;
import co.q64.stars.tile.FormingTileFactory;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.inject.Inject;
import javax.inject.Singleton;

@Getter
@Singleton
public class FormingBlock extends BaseBlock {
    protected @Inject FormingTileFactory tileFactory;

    protected @Inject FormingBlock() {
        super("forming", Properties.create(Material.IRON).hardnessAndResistance(1.5f, 6.0f));
        //setDefaultState(getStateContainer().getBaseState().with(BlockStates.TYPE, 0));
    }

    /*
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(BlockStates.FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer();
    }
    */

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        //return VoxelShapes.empty();
        return VoxelShapes.fullCube();
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileFactory.create();
    }
}
