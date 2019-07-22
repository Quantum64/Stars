package co.q64.stars.net.packets;

import co.q64.stars.util.ClientNetHandler;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class PlayClientEffectPacket {
    private ClientEffectType type;
    private ClientNetHandler clientNetHandler;

    protected PlayClientEffectPacket(@Provided ClientNetHandler clientNetHandler, PacketBuffer buffer) {
        CompoundNBT tag = buffer.readCompoundTag();
        this.clientNetHandler = clientNetHandler;
        this.type = ClientEffectType.valueOf(tag.getString("type"));
    }

    protected PlayClientEffectPacket(@Provided ClientNetHandler clientNetHandler, ClientEffectType type) {
        this.clientNetHandler = clientNetHandler;
        this.type = type;
    }

    public void encode(PacketBuffer buffer) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("type", type.name());
        buffer.writeCompoundTag(tag);
    }

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            switch (type) {
                case ENTRY:
                    clientNetHandler.playEntryEffect();
                    break;
                case DARKNESS:
                    clientNetHandler.playDarknessEffect();
                    break;
            }
        });
        context.get().setPacketHandled(true);
    }

    public static enum ClientEffectType {
        ENTRY, DARKNESS
    }
}

