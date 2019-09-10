package co.q64.stars.tile;

import co.q64.stars.block.SpecialAirBlock;
import co.q64.stars.entity.PickupEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.server.ServerWorld;

import javax.inject.Inject;

public class ChallengeExitTile extends TileEntity implements ITickableTileEntity {
    protected @Inject EntityType<PickupEntity> pickupEntityType;
    protected @Inject SpecialAirBlock specialAirBlock;

    private int ticks = 0;

    @Inject
    protected ChallengeExitTile(TileEntityType<ChallengeExitTile> type) {
        super(type);
    }

    public void tick() {
        if (!world.isRemote) {
            if (ticks == 20) {
                long locId = pos.toLong();
                ((ServerWorld) world).getEntities()
                        .filter(entity -> entity instanceof PickupEntity)
                        .map(entity -> (PickupEntity) entity)
                        .filter(entity -> entity.getVariant() == PickupEntity.VARIANT_STAR)
                        .filter(entity -> entity.getPosition().distanceSq(pos) < 1000 * 1000)
                        .forEach(Entity::remove);
                PickupEntity pickupEntity = pickupEntityType.create(world);
                pickupEntity.setVariant(PickupEntity.VARIANT_HEART);
                pickupEntity.setLocationId(locId);
                pickupEntity.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                world.addEntity(pickupEntity);
                world.setBlockState(pos, specialAirBlock.getDefaultState());
            }
            ticks++;
        }
    }
}
