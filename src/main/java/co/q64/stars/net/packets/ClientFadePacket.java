package co.q64.stars.net.packets;

import co.q64.stars.net.ClientNetHandler;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class ClientFadePacket {
    private FadeMode type;
    private long time;
    private ClientNetHandler clientNetHandler;

    protected ClientFadePacket(@Provided ClientNetHandler clientNetHandler, PacketBuffer buffer) {
        CompoundNBT tag = buffer.readCompoundTag();
        this.clientNetHandler = clientNetHandler;
        this.type = FadeMode.valueOf(tag.getString("forming"));
        this.time = tag.getLong("time");
    }

    protected ClientFadePacket(@Provided ClientNetHandler clientNetHandler, FadeMode type, long time) {
        this.clientNetHandler = clientNetHandler;
        this.type = type;
        this.time = time;
    }

    public void encode(PacketBuffer buffer) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("forming", type.name());
        tag.putLong("time", time);
        buffer.writeCompoundTag(tag);
    }

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            clientNetHandler.fade(type, time);
        });
        context.get().setPacketHandled(true);
    }

    public static enum FadeMode {
        FADE_TO_WHITE, FADE_FROM_WHITE, FADE_TO_BLACK, FADE_FROM_BLACK
    }
}

