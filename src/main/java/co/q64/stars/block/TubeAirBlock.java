package co.q64.stars.block;

import co.q64.stars.tile.TubeTile;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
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
public class TubeAirBlock extends BaseBlock {
    protected @Inject Provider<TubeTile> tileProvider;

    protected @Inject TubeAirBlock() {
        super("tube_air", Properties.create(Material.IRON).hardnessAndResistance(0f, 0f));
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        TubeTile result = tileProvider.get();
        result.setAir(true);
        return result;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return true;
    }
}
