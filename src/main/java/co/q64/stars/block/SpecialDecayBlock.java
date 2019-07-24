package co.q64.stars.block;

import co.q64.stars.util.DecayManager.SpecialDecayType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SpecialDecayBlock extends DecayBlock {
    public static final EnumProperty<SpecialDecayType> TYPE = EnumProperty.create("type", SpecialDecayType.class);

    protected @Inject SpecialDecayBlock() {
        super("special_decay", Properties.create(Material.GLASS).hardnessAndResistance(-1f, 3600000f));
        setDefaultState(getDefaultState().with(TYPE, SpecialDecayType.HEART));
    }

    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder.add(TYPE));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }
}
