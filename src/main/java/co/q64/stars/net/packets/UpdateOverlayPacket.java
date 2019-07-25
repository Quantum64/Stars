package co.q64.stars.net.packets;

import co.q64.stars.capability.GardenerCapability;
import co.q64.stars.net.ClientNetHandler;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

@AutoFactory
public class UpdateOverlayPacket {
    private Capability<GardenerCapability> capability;
    private GardenerCapability gardenerCapability;
    private ClientNetHandler clientNetHandler;

    protected UpdateOverlayPacket(PacketBuffer buffer, @Provided Capability<GardenerCapability> capability, @Provided ClientNetHandler clientNetHandler) {
        CompoundNBT tag = buffer.readCompoundTag();
        this.clientNetHandler = clientNetHandler;
        this.capability = capability;
        this.gardenerCapability = capability.getDefaultInstance();
        capability.getStorage().readNBT(capability, gardenerCapability, null, tag);
    }

    protected UpdateOverlayPacket(GardenerCapability gardenerCapability, @Provided Capability<GardenerCapability> capability, @Provided ClientNetHandler clientNetHandler) {
        this.clientNetHandler = clientNetHandler;
        this.gardenerCapability = gardenerCapability;
        this.capability = capability;
    }

    public void encode(PacketBuffer buffer) {
        CompoundNBT tag = (CompoundNBT) capability.writeNBT(gardenerCapability, null);
        buffer.writeCompoundTag(tag);
    }

    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            clientNetHandler.updateOverlay(gardenerCapability);
        });
        context.get().setPacketHandled(true);
    }
}

