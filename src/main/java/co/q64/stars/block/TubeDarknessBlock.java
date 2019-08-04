package co.q64.stars.block;

import co.q64.stars.tile.TubeTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.IBlockReader;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class TubeDarknessBlock extends BaseBlock {
    protected @Inject Provider<TubeTile> tileProvider;

    protected TubeDarknessBlock(String id, Properties settings) {
        super(id, settings);
    }

    protected @Inject TubeDarknessBlock() {
        this("tube_darkness", Properties.create(Material.GLASS).hardnessAndResistance(-1f, 3600000f));
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileProvider.get();
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
