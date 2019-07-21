package co.q64.stars.tile;

import co.q64.stars.block.FormingBlock;
import co.q64.stars.tile.type.SeedTileType;
import co.q64.stars.type.FormingBlockType;
import co.q64.stars.type.FormingBlockTypes;
import co.q64.stars.type.forming.RedFormingBlockType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;

import javax.inject.Inject;

public class SeedTile extends SyncTileEntity implements ITickableTileEntity {
    protected @Inject FormingBlockTypes types;
    protected @Inject FormingBlock formingBlock;
    protected @Inject RedFormingBlockType redFormingBlockType;

    private Direction direction;
    private @Getter @Setter int growTicks = getInitialGrowTicks();
    private @Getter @Setter boolean calculated;
    private @Getter @Setter FormingBlockType formingBlockType;
    private @Getter @Setter FormingBlockType seedType;
    private @Getter @Setter boolean primed = false;
    private @Getter @Setter boolean fruit = false;

    private int ticks = 0;

    @Inject
    protected SeedTile(SeedTileType type) {
        super(type);
    }

    protected SeedTile(TileEntityType<?> type) {
        super(type);
    }

    @Inject
    protected void setupDefault(FormingBlockTypes types) {
        this.formingBlockType = types.getDefault();
        this.seedType = types.getDefault();
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        formingBlockType = types.get(compound.getInt("formingBlockType"));
        seedType = types.get(compound.getInt("seedType"));
        calculated = compound.getBoolean("calculated");
        primed = compound.getBoolean("primed");
        growTicks = compound.getInt("growTicks");
        fruit = compound.getBoolean("fruit");
    }

    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT result = super.write(compound);
        result.putInt("formingBlockType", formingBlockType.getId());
        result.putInt("seedType", seedType.getId());
        result.putBoolean("calculated", calculated);
        result.putBoolean("primed", primed);
        result.putInt("growTicks", growTicks);
        result.putBoolean("fruit", fruit);
        return result;
    }

    protected boolean hasSeed() {
        return true;
    }

    protected int getInitialGrowTicks() {
        return 20;
    }

    public void tick() {
        if (!world.isRemote) {
            if (!hasSeed()) {
                return;
            }
            if (ticks == growTicks) {
                if (isPrimed()) {
                    redFormingBlockType.explode((ServerWorld) world, pos, false);
                } else {
                    direction = seedType.getInitialDirection(world, pos);
                    if (direction == null) {
                        return;
                    }
                    BlockPos target = pos.offset(direction);
                    world.setBlockState(target, formingBlock.getDefaultState());

                    FormingTile tile = (FormingTile) world.getTileEntity(target);
                    if (tile == null) {
                        System.out.println("null tile on seed block grow " + target.toString());
                    } else {
                        tile.setFirst(true);
                        tile.setDirection(direction);
                        tile.setup(seedType);
                        tile.setCalculated(true);
                    }
                }
            }
            if (ticks > growTicks) {
                if (direction == null) {
                    return;
                }
                if (world.getBlockState(pos.offset(direction)).getBlock() != formingBlock) {
                    world.setBlockState(pos, formingBlockType.getFormedBlock().getDefaultState());
                }
            }
            ticks++;
        }
    }
}
