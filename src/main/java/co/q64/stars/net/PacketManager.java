package co.q64.stars.net;

import co.q64.stars.util.Identifiers;
import lombok.Getter;
import net.minecraftforge.fml.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PacketManager {
    private static final String protocolVersion = "1";

    private @Getter SimpleChannel channel;

    protected @Inject PacketManager(Identifiers identifiers) {
        this.channel = ChannelBuilder.named(identifiers.get("main"))
                .clientAcceptedVersions(protocolVersion::equals)
                .serverAcceptedVersions(protocolVersion::equals)
                .networkProtocolVersion(() -> protocolVersion)
                .simpleChannel();
    }

    public void register() {
        int id = 0;
    }
}
