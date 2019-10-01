package co.q64.stars.net;

import co.q64.stars.net.packets.ClientFadePacket;
import co.q64.stars.net.packets.ClientFadePacketFactory;
import co.q64.stars.net.packets.LostPacket;
import co.q64.stars.net.packets.LostPacketFactory;
import co.q64.stars.net.packets.PlantSeedPacket;
import co.q64.stars.net.packets.PlantSeedPacketFactory;
import co.q64.stars.net.packets.UpdateJumpPacket;
import co.q64.stars.net.packets.UpdateJumpPacketFactory;
import co.q64.stars.net.packets.UpdateOverlayPacket;
import co.q64.stars.net.packets.UpdateOverlayPacketFactory;
import co.q64.stars.util.Identifiers;
import lombok.Getter;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PacketManager {
    private static final String protocolVersion = "1";

    protected @Getter @Inject ClientFadePacketFactory clientFadePacketFactory;
    protected @Getter @Inject UpdateJumpPacketFactory updateJumpPacketFactory;
    protected @Getter @Inject UpdateOverlayPacketFactory updateOverlayPacketFactory;
    protected @Getter @Inject PlantSeedPacketFactory plantSeedPacketFactory;
    protected @Getter @Inject LostPacketFactory lostPacketFactory;

    private @Getter SimpleChannel channel;

    protected @Inject PacketManager(Identifiers identifiers) {
        this.channel = ChannelBuilder.named(identifiers.get("main"))
                .clientAcceptedVersions(v -> protocolVersion.equals(v) || v.equals(NetworkRegistry.ABSENT))
                .serverAcceptedVersions(v -> protocolVersion.equals(v) || v.equals(NetworkRegistry.ABSENT))
                .networkProtocolVersion(() -> protocolVersion)
                .simpleChannel();
    }

    public void register() {
        int id = 0;
        channel.registerMessage(id++, ClientFadePacket.class, (packet, buffer) -> packet.encode(buffer), buffer -> clientFadePacketFactory.create(buffer), (packet, context) -> packet.handle(context));
        channel.registerMessage(id++, UpdateJumpPacket.class, (packet, buffer) -> packet.encode(buffer), buffer -> updateJumpPacketFactory.create(buffer), (packet, context) -> packet.handle(context));
        channel.registerMessage(id++, UpdateOverlayPacket.class, (packet, buffer) -> packet.encode(buffer), buffer -> updateOverlayPacketFactory.create(buffer), (packet, context) -> packet.handle(context));
        channel.registerMessage(id++, PlantSeedPacket.class, (packet, buffer) -> packet.encode(buffer), buffer -> plantSeedPacketFactory.create(buffer), (packet, context) -> packet.handle(context));
        channel.registerMessage(id++, LostPacket.class, (packet, buffer) -> packet.encode(buffer), buffer -> lostPacketFactory.create(buffer), (packet, context) -> packet.handle(context));
    }
}
