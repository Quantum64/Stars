package co.q64.stars.net.packets;

import co.q64.stars.util.EntryManager;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class PlantSeedPacket {
    private EntryManager entryManager;

    protected PlantSeedPacket(@Provided EntryManager entryManager, PacketBuffer buffer) {
        this.entryManager = entryManager;
    }

    protected PlantSeedPacket(@Provided EntryManager entryManager) {
        this.entryManager = entryManager;
    }

    public void encode(PacketBuffer buffer) {}

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            entryManager.grow(context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }
}

