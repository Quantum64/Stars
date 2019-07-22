package co.q64.stars.entity;

import co.q64.stars.util.EntryManager;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
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
    public static final int VARIANT_HEART = 0, VARIANT_KEY = 1;

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(PickupEntity.class, DataSerializers.VARINT);
    private EntryManager entryManager;
    private @Getter int age;

    protected PickupEntity(@Provided EntityType<PickupEntity> type, World world, @Provided EntryManager entryManager) {
        super(type, world);
        this.entryManager = entryManager;
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
                        entryManager.addSeed((ServerPlayerEntity) player);
                        break;
                    case 1:
                        entryManager.addKey((ServerPlayerEntity) player);
                        break;
                }
                remove();
            }
        }
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
    }

    protected void writeAdditional(CompoundNBT tag) {
        tag.putInt("variant", getVariant());
    }
}
