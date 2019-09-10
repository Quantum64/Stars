package co.q64.stars.tile;

import co.q64.stars.block.DarknessBlock;
import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.entity.PickupEntity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import javax.inject.Inject;
import java.util.Optional;

public class DoorTile extends SyncTileEntity implements ITickableTileEntity {
    private static final int SEARCH_DEPTH = 30;

    protected @Inject SpecialAirBlock specialAirBlock;
    protected @Inject EntityType<PickupEntity> pickupEntityType;

    private @Setter @Getter boolean fallen = false;
    private @Setter @Getter boolean challenge = false;
    private int ticks;

    @Inject
    public DoorTile(TileEntityType<DoorTile> type) {
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
            BlockPos updated = pos;
            for (int attempt = 1; attempt < SEARCH_DEPTH; attempt++) {
                BlockPos pos = getPos().offset(Direction.DOWN, attempt);
                updated = pos;
                if (world.getBlockState(updated).getBlock() instanceof DarknessBlock) {
                    if (world.setBlockState(updated, getBlockState()) && world.setBlockState(getPos(), specialAirBlock.getDefaultState())) {
                        Optional.ofNullable((DoorTile) world.getTileEntity(updated)).ifPresent(tile -> {
                            tile.setFallen(true);
                            tile.setChallenge(challenge);
                            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
                            fallen = true;
                        });
                        break;
                    }
                }
            }
            if (!fallen) {
                updated = getPos().offset(Direction.DOWN);
                world.setBlockState(updated, getBlockState());
                world.setBlockState(getPos(), specialAirBlock.getDefaultState());
                Optional.ofNullable((DoorTile) world.getTileEntity(updated)).ifPresent(tile -> {
                    tile.setFallen(true);
                    tile.setChallenge(challenge);
                    world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
                });
            }
            PickupEntity pickup = pickupEntityType.create(world);
            pickup.setPosition(updated.getX() + 0.5, updated.getY() + 1, updated.getZ() + 0.5);
            pickup.setVariant(PickupEntity.VARIANT_ARROW);
            world.addEntity(pickup);
        }
        ticks++;
    }
}
