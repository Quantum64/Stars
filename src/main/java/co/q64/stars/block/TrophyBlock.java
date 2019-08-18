package co.q64.stars.block;

import co.q64.stars.tile.TrophyTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
public class TrophyBlock extends BaseBlock {
    private static final VoxelShape SHAPE = VoxelShapes.or(
            Block.makeCuboidShape(3, 0, 3, 13, 2, 13),
            Block.makeCuboidShape(6, 2, 6, 10, 6, 10));

    protected @Inject Provider<TrophyTile> tileFactory;

    protected @Inject TrophyBlock() {
        super("trophy", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 6f).lightValue(15));
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileFactory.get();
    }
}
