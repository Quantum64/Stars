package co.q64.stars.block;

import co.q64.stars.tile.SpecialDecayEdgeTile;
import co.q64.stars.tile.SpecialDecayEdgeTile.SpecialDecayType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class SpecialDecayEdgeBlock extends BaseBlock {
    protected @Inject Provider<SpecialDecayEdgeTile> tileFactory;

    protected @Inject SpecialDecayEdgeBlock() {
        super("special_decay_edge", Properties.create(Material.GLASS).hardnessAndResistance(-1f, 3600000f));
        setDefaultState(getDefaultState().with(SpecialDecayBlock.TYPE, SpecialDecayType.HEART));
    }

    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder.add(SpecialDecayBlock.TYPE));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        SpecialDecayEdgeTile tile = tileFactory.get();
        tile.setDecayType(state.get(SpecialDecayBlock.TYPE));
        return tile;
    }
}
