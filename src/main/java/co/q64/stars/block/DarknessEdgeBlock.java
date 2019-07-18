package co.q64.stars.block;

import co.q64.stars.tile.DarknessEdgeTileFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DarknessEdgeBlock extends BaseBlock {
    protected @Inject DarknessEdgeTileFactory tileFactory;

    protected @Inject DarknessEdgeBlock() {
        super("darkness_edge", Properties.create(Material.IRON).hardnessAndResistance(1.5f, 6.0f));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileFactory.create();
    }
}