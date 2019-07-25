package co.q64.stars.net.packets;

import co.q64.stars.net.ServerNetHandler;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class PlantSeedPacket {
    private ServerNetHandler serverNetHandler;

    protected PlantSeedPacket(@Provided ServerNetHandler serverNetHandler, PacketBuffer buffer) {
        this.serverNetHandler = serverNetHandler;
    }

    protected PlantSeedPacket(@Provided ServerNetHandler serverNetHandler) {
        this.serverNetHandler = serverNetHandler;
    }

    public void encode(PacketBuffer buffer) {}

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            serverNetHandler.grow(context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }
}

