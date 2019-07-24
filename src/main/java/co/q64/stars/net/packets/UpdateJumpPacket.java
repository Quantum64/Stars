package co.q64.stars.net.packets;

import co.q64.stars.util.FleetingManager;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class UpdateJumpPacket {
    private FleetingManager fleetingManager;
    private boolean jumping;

    protected UpdateJumpPacket(@Provided FleetingManager fleetingManager, PacketBuffer buffer) {
        this.fleetingManager = fleetingManager;
        this.jumping = buffer.readBoolean();
    }

    protected UpdateJumpPacket(@Provided FleetingManager fleetingManager, boolean jumping) {
        this.fleetingManager = fleetingManager;
        this.jumping = jumping;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(jumping);
    }

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            fleetingManager.updateJumpStatus(context.get().getSender(), jumping);
        });
        context.get().setPacketHandled(true);
    }
}

