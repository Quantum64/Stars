package co.q64.stars.block;

import co.q64.stars.level.LevelType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer.Builder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GatewayBlock extends BaseBlock {
    public static final EnumProperty<LevelType> TYPE = EnumProperty.create("type", LevelType.class);
    public static final BooleanProperty COMPLETE = BooleanProperty.create("complete");

    protected @Inject GatewayBlock() {
        super("gateway", Properties.create(Material.GLASS).hardnessAndResistance(-1f, 3600000f));
        setDefaultState(getDefaultState().with(TYPE, LevelType.RED).with(COMPLETE, false));
    }

    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder.add(TYPE).add(COMPLETE));
    }
}
