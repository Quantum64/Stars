package co.q64.stars.net.packets;

import co.q64.stars.net.ServerNetHandler;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class UpdateJumpPacket {
    private ServerNetHandler serverNetHandler;
    private boolean jumping;

    protected UpdateJumpPacket(@Provided ServerNetHandler serverNetHandler, PacketBuffer buffer) {
        this.serverNetHandler = serverNetHandler;
        this.jumping = buffer.readBoolean();
    }

    protected UpdateJumpPacket(@Provided ServerNetHandler serverNetHandler, boolean jumping) {
        this.serverNetHandler = serverNetHandler;
        this.jumping = jumping;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(jumping);
    }

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            serverNetHandler.updateJumpStatus(context.get().getSender(), jumping);
        });
        context.get().setPacketHandled(true);
    }
}

