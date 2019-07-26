package co.q64.stars.entity;

import co.q64.stars.util.FleetingManager;
import co.q64.stars.util.PlayerManager;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

@AutoFactory
public class PickupEntity extends Entity {
    public static final int VARIANT_HEART = 0, VARIANT_KEY = 1, VARIANT_STAR = 2;

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(PickupEntity.class, DataSerializers.VARINT);
    private FleetingManager fleetingManager;
    private PlayerManager playerManager;
    private @Getter @Setter long locationId;
    private @Getter int age;

    protected PickupEntity(@Provided EntityType<PickupEntity> type, World world, @Provided FleetingManager fleetingManager, @Provided PlayerManager playerManager) {
        super(type, world);
        this.fleetingManager = fleetingManager;
        this.playerManager = playerManager;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void tick() {
        age++;
        super.tick();
    }

    public void onCollideWithPlayer(PlayerEntity player) {
        if (!world.isRemote && player instanceof ServerPlayerEntity) {
            if (player.getBoundingBox().intersects(getBoundingBox())) {
                switch (getVariant()) {
                    case 0:
                        playerManager.addSeed((ServerPlayerEntity) player);
                        break;
                    case 1:
                        fleetingManager.addKey((ServerPlayerEntity) player);
                        break;
                }
                remove();
            }
        }
    }

    public boolean isGlowing() {
        return true;
    }

    public int getVariant() {
        return getDataManager().get(VARIANT);
    }

    public void setVariant(int variant) {
        getDataManager().set(VARIANT, variant);
    }

    protected void registerData() {
        getDataManager().register(VARIANT, 0);
    }

    protected void readAdditional(CompoundNBT tag) {
        if (tag.contains("variant")) {
            setVariant(tag.getInt("variant"));
        }
        if (tag.contains("locationId")) {
            setLocationId(tag.getLong("locationId"));
        }
    }

    protected void writeAdditional(CompoundNBT tag) {
        tag.putInt("variant", getVariant());
        tag.putLong("locationId", getLocationId());
    }
}
