package co.q64.stars.client.listener;

import co.q64.stars.listener.Listener;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ClientRegistryListener implements Listener {

    protected @Inject ClientRegistryListener() {}

    @SubscribeEvent
    public void modelLoadEvent(ModelRegistryEvent event) {
    }
}
