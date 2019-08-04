package co.q64.stars.net.packets;

import co.q64.stars.net.ServerNetHandler;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class LostPacket {
    private ServerNetHandler serverNetHandler;

    protected LostPacket(@Provided ServerNetHandler serverNetHandler, PacketBuffer buffer) {
        this.serverNetHandler = serverNetHandler;
    }

    protected LostPacket(@Provided ServerNetHandler serverNetHandler) {
        this.serverNetHandler = serverNetHandler;
    }

    public void encode(PacketBuffer buffer) {}

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            serverNetHandler.lost(context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }
}

