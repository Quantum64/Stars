package co.q64.stars.state;

import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public interface BlockStates {
    public static final IntegerProperty TYPE = IntegerProperty.create("formingBlockType", 0, 15);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
}
