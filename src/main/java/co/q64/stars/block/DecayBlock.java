package co.q64.stars.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DecayBlock extends BaseBlock {

    protected DecayBlock(String id, Properties settings) {
        super(id, settings);
    }

    protected @Inject DecayBlock() {
        super("decay", Properties.create(Material.IRON).hardnessAndResistance(1.5f, 6.0f));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }
}
