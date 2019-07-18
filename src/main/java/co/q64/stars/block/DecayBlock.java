package co.q64.stars.block;

import net.minecraft.block.material.Material;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DecayBlock extends BaseBlock {

    protected @Inject DecayBlock() {
        super("decay", Properties.create(Material.IRON).hardnessAndResistance(1.5f, 6.0f));
    }

    /*
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }
     */
}
