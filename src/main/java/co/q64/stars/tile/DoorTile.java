package co.q64.stars.tile;

import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.tile.type.DoorTileType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.inject.Inject;
import java.util.Optional;

public class DoorTile extends SyncTileEntity implements ITickableTileEntity {
    private static final int SEARCH_DEPTH = 30;

    protected @Inject SpecialAirBlock specialAirBlock;

    private @Setter @Getter boolean fallen = false;
    private int ticks;

    @Inject
    public DoorTile(DoorTileType type) {
        super(type);
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        this.fallen = compound.getBoolean("fallen");
    }

    public CompoundNBT write(CompoundNBT compound) {
        compound.putBoolean("fallen", fallen);
        return super.write(compound);
    }

    @Override
    public void tick() {
        if (fallen || world.isRemote) {
            return;
        }
        if (ticks == 20) {
            for (int attempt = 1; attempt < SEARCH_DEPTH; attempt++) {
                BlockPos pos = getPos().offset(Direction.DOWN, attempt);
                if (world.getBlockState(pos).getBlock() instanceof DarknessBlock) {
                    if (world.setBlockState(pos, getBlockState()) && world.setBlockState(getPos(), specialAirBlock.getDefaultState())) {
                        DoorTile tile = (DoorTile) world.getTileEntity(pos);
                        tile.setFallen(true);
                        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
                        return;
                    }
                }
            }
            world.setBlockState(getPos().offset(Direction.DOWN), getBlockState());
            world.setBlockState(getPos(), specialAirBlock.getDefaultState());
            Optional.ofNullable((DoorTile) world.getTileEntity(getPos().offset(Direction.DOWN))).ifPresent(tile -> {
                tile.setFallen(true);
                world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
            });
        }
        ticks++;
    }
}
