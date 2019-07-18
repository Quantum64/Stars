package co.q64.stars.block;

import co.q64.stars.tile.DecayingTileFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DecayingBlock extends BaseBlock {
    protected @Inject DecayingTileFactory tileFactory;

    protected @Inject DecayingBlock() {
        super("decaying", Properties.create(Material.IRON).hardnessAndResistance(1.5f, 6.0f));
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileFactory.create();
    }
}
