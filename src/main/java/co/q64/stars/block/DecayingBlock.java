package co.q64.stars.block;

import co.q64.stars.tile.DecayingTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class DecayingBlock extends BaseBlock {
    protected @Inject Provider<DecayingTile> tileFactory;

    protected @Inject DecayingBlock() {
        super("decaying", Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f));
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileFactory.get();
    }

    private DecayingBlock(String id, Properties properties) {
        super(id, properties);
    }

    @Singleton
    public static class DecayingBlockHard extends DecayingBlock implements HardBlock {
        protected @Inject DecayingBlockHard() {
            super("decaying_hard", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f));
        }
    }
}
