package co.q64.stars.listener;

import co.q64.stars.dimension.AdventureDimension;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.EntityEvent.EyeHeight;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Field;

@Singleton
public class PlayerListener implements Listener {
    private EntitySize size = new EntitySize(0.6f, 0.8f, false);
    private Field sizeField;

    @Inject
    protected PlayerListener() {
        for (Field field : Entity.class.getDeclaredFields()) {
            if (field.getType().isAssignableFrom(EntitySize.class)) {
                if (sizeField != null) {
                    throw new IllegalStateException("Too many size fields");
                }
                sizeField = field;
            }
        }
        sizeField.setAccessible(true);
    }

    @SubscribeEvent
    public void onPlayerLoad(PlayerEvent.LoadFromFile event) {
        if (event.getEntityPlayer().dimension == null) {
            // In unregistered dimension
            // TODO
            event.getEntityPlayer().dimension = DimensionType.OVERWORLD;
        }
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (event.getWorld().getDimension() instanceof AdventureDimension) {
            }
        }
    }

    @SubscribeEvent
    public void onEyeHeight(EyeHeight event) throws IllegalAccessException {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity entity = (PlayerEntity) event.getEntity();
            if (event.getEntity().getEntityWorld().getDimension() instanceof AdventureDimension) {
                event.setNewHeight(0.8f);
                if (entity.getBoundingBox().getYSize() < size.height - 0.02 || entity.getBoundingBox().getYSize() > size.height + 0.02) {
                    sizeField.set(entity, size);
                    AxisAlignedBB aabb = entity.getBoundingBox();
                    entity.setBoundingBox(new AxisAlignedBB(aabb.minX, aabb.minY, aabb.minZ, aabb.minX + size.width, aabb.minY + size.height, aabb.minZ + size.width));
                }
            }
        }
    }
}
