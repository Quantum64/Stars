package co.q64.stars.listener;

import co.q64.stars.command.StarsCommand;
import co.q64.stars.net.PacketManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InitializationListener implements Listener {
    protected @Inject PacketManager packetManager;
    protected @Inject StarsCommand command;

    protected @Inject InitializationListener() {}

    @SubscribeEvent
    public void onInitialize(FMLCommonSetupEvent event) {
        packetManager.register();
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        command.register(event.getCommandDispatcher());
    }
}
