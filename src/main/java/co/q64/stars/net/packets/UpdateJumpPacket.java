package co.q64.stars.net.packets;

import co.q64.stars.util.EntryManager;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class UpdateJumpPacket {
    private EntryManager entryManager;
    private boolean jumping;

    protected UpdateJumpPacket(@Provided EntryManager entryManager, PacketBuffer buffer) {
        this.entryManager = entryManager;
        this.jumping = buffer.readBoolean();
    }

    protected UpdateJumpPacket(@Provided EntryManager entryManager, boolean jumping) {
        this.entryManager = entryManager;
        this.jumping = jumping;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(jumping);
    }

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            entryManager.updateJumpStatus(context.get().getSender(), jumping);
        });
        context.get().setPacketHandled(true);
    }

    protected static enum ClientEffectType {
        ENTRY, DARKNESS
    }
}

