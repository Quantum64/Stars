package co.q64.stars.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DarknessBlock extends BaseBlock {

    protected DarknessBlock(String id, Properties settings) {
        super(id, settings);
    }

    protected @Inject DarknessBlock() {
        super("darkness", Properties.create(Material.GLASS).hardnessAndResistance(-1f, 3600000f));
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
