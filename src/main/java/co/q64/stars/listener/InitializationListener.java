package co.q64.stars.listener;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.capability.HubCapability;
import co.q64.stars.capability.gardener.GardenerCapabilityFactory;
import co.q64.stars.capability.gardener.GardenerCapabilityStorage;
import co.q64.stars.capability.hub.HubCapabilityFactory;
import co.q64.stars.capability.hub.HubCapabilityStorage;
import co.q64.stars.command.StarsCommand;
import co.q64.stars.dimension.fleeting.FleetingDimension.FleetingDimensionTemplate;
import co.q64.stars.dimension.fleeting.FleetingSolidDimension.FleetingSolidDimensionTemplate;
import co.q64.stars.net.PacketManager;
import co.q64.stars.util.PlayerManager;
import co.q64.stars.util.Scheduler;
import co.q64.stars.util.Structures;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;

@Singleton
public class InitializationListener implements Listener {
    protected @Inject PacketManager packetManager;
    protected @Inject StarsCommand command;
    protected @Inject GardenerCapabilityStorage gardenerCapabilityStorage;
    protected @Inject GardenerCapabilityFactory gardenerCapabilityFactory;
    protected @Inject HubCapabilityStorage hubCapabilityStorage;
    protected @Inject HubCapabilityFactory hubCapabilityFactory;
    protected @Inject FleetingDimensionTemplate fleetingDimensionTemplate;
    protected @Inject FleetingSolidDimensionTemplate fleetingSolidDimensionTemplate;
    protected @Inject Scheduler scheduler;
    protected @Inject PlayerManager playerManager;
    protected @Inject Structures structures;

    protected @Inject InitializationListener() {}

    @SubscribeEvent
    public void onInitialize(FMLCommonSetupEvent event) {
        packetManager.register();
        CapabilityManager.INSTANCE.register(GardenerCapability.class, gardenerCapabilityStorage, gardenerCapabilityFactory);
        CapabilityManager.INSTANCE.register(HubCapability.class, hubCapabilityStorage, hubCapabilityFactory);
    }

    @SubscribeEvent
    public void onServerPreInit(FMLServerStartedEvent event) {
        for (DimensionType type : Arrays.asList(fleetingDimensionTemplate.getType(), fleetingSolidDimensionTemplate.getType())) {
            try {
                Files.walk(type.getDirectory(event.getServer().getWorld(type).getSaveHandler().getWorldDirectory()).toPath())
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
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        command.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(structures);
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent event) {
        if (event.phase == Phase.START) {
            scheduler.tick();
            playerManager.tick();
        }
    }
}
