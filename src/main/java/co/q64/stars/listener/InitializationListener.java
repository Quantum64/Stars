package co.q64.stars.listener;

import co.q64.stars.command.StarsCommand;
import co.q64.stars.dimension.Dimensions;
import co.q64.stars.net.PacketManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;

@Singleton
public class InitializationListener implements Listener {
    protected @Inject PacketManager packetManager;
    protected @Inject Dimensions dimensions;
    protected @Inject StarsCommand command;

    protected @Inject InitializationListener() {}

    @SubscribeEvent
    public void onInitialize(FMLCommonSetupEvent event) {
        packetManager.register();
    }

    @SubscribeEvent
    public void onServerPreInit(FMLServerStartedEvent event) {
        dimensions.register();
        File dimension = dimensions.getAdventureDimensionType().getDirectory(event.getServer().getWorld(dimensions.getAdventureDimensionType()).getSaveHandler().getWorldDirectory());
        try {
            Files.walk(dimension.toPath())
                    .sorted(Comparator.reverseOrder())
                    .forEach(t -> {
                        try {
                            Files.delete(t);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        command.register(event.getCommandDispatcher());
    }
}