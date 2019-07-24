package co.q64.stars.net.packets;

import co.q64.stars.util.FleetingManager;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class PlantSeedPacket {
    private FleetingManager fleetingManager;

    protected PlantSeedPacket(@Provided FleetingManager fleetingManager, PacketBuffer buffer) {
        this.fleetingManager = fleetingManager;
    }

    protected PlantSeedPacket(@Provided FleetingManager fleetingManager) {
        this.fleetingManager = fleetingManager;
    }

    public void encode(PacketBuffer buffer) {}

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            fleetingManager.grow(context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }
}

