package co.q64.stars.tile;

import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.SpecialDecayBlock;
import co.q64.stars.entity.PickupEntity;
import co.q64.stars.tile.type.SpecialDecayEdgeTileType;
import co.q64.stars.util.DecayManager.SpecialDecayType;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import javax.inject.Inject;

public class SpecialDecayEdgeTile extends DecayEdgeTile implements ITickableTileEntity {
    protected @Inject EntityType<PickupEntity> pickupEntityType;

    private @Setter @Getter SpecialDecayType decayType = SpecialDecayType.HEART;
    private boolean first = true;

    @Inject
    protected SpecialDecayEdgeTile(SpecialDecayEdgeTileType type) {
        super(type);
    }

    @Inject
    protected void completeSetup(SpecialDecayBlock block) {
        this.decayBlock = block;
    }

    public void tick() {
        if (!world.isRemote && first) {
            first = false;
            long locId = pos.toLong();
            for (PickupEntity entity : world.getEntitiesWithinAABB(PickupEntity.class, new AxisAlignedBB(pos))) {
                if (entity.getLocationId() == locId) {
                    return;
                }
            }
            PickupEntity pickupEntity = pickupEntityType.create(world);
            pickupEntity.setVariant(2);
            pickupEntity.setLocationId(locId);
            pickupEntity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            world.addEntity(pickupEntity);
        }
        super.tick();
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        this.decayType = SpecialDecayType.valueOf(compound.getString("decayType"));
    }

    public CompoundNBT write(CompoundNBT compound) {
        compound.putString("decayType", decayType.name());
        return super.write(compound);
    }

    protected BlockState getDecayState(DecayBlock block) {
        return block.getDefaultState().with(SpecialDecayBlock.TYPE, decayType);
    }
}
