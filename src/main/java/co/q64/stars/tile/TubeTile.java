package co.q64.stars.tile;

import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.tile.type.TubeTileType;
import lombok.Setter;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

import javax.inject.Inject;
import java.util.Optional;

public class TubeTile extends TileEntity implements ITickableTileEntity {
    protected @Inject DarknessBlock darknessBlock;
    protected @Inject SpecialAirBlock specialAirBlock;

    private @Setter boolean air;

    @Inject
    public TubeTile(TubeTileType type) {
        super(type);
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }
        if (pos.getY() > 4) {
            world.setBlockState(pos.offset(Direction.DOWN, 2), getBlockState());
            Optional.ofNullable((TubeTile) world.getTileEntity(pos.offset(Direction.DOWN, 2))).ifPresent(tile -> {
                tile.setAir(air);
            });
        }
        world.setBlockState(pos, air ? specialAirBlock.getDefaultState() : darknessBlock.getDefaultState());
        world.setBlockState(pos.offset(Direction.DOWN), air ? specialAirBlock.getDefaultState() : darknessBlock.getDefaultState());
    }
}
