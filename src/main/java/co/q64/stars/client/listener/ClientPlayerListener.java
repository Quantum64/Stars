package co.q64.stars.client.listener;

import co.q64.stars.dimension.AdventureDimension;
import co.q64.stars.listener.Listener;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientPlayerListener implements Listener {
    private Boolean autoJump;

    protected @Inject ClientPlayerListener() {}

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() == Minecraft.getInstance().player) {
            if (event.getWorld().getDimension() instanceof AdventureDimension) {
                autoJump = Minecraft.getInstance().gameSettings.autoJump;
                Minecraft.getInstance().gameSettings.autoJump = false;
            } else {
                if (autoJump != null) {
                    Minecraft.getInstance().gameSettings.autoJump = autoJump;
                    autoJump = null;
                }
            }
        }
    }
}
