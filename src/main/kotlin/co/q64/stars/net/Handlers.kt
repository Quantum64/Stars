package co.q64.stars.net

import co.q64.stars.util.load
import co.q64.stars.util.toNBT
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fml.network.NetworkEvent
import java.util.function.Supplier

@Serializable
open class Packet(val type: PacketType)

class PacketContainer<T : Packet> {
    private val data: T?
    private val serializer: KSerializer<T>?

    constructor(data: T, serializer: KSerializer<T>) {
        this.data = data
        this.serializer = serializer
    }

    constructor(buffer: PacketBuffer) {
        val tag = buffer.readCompoundTag()
        this.serializer = tag?.load(Packet.serializer())?.type?.serializer as? KSerializer<T>
        this.data = serializer?.let {
            tag?.load(serializer)
        }
    }

    fun encode(buffer: PacketBuffer) {
        serializer?.let {
            buffer.writeCompoundTag(serializer.toNBT(data ?: return))
        }
    }

    fun handle(context: Supplier<NetworkEvent.Context>) {
        context.get().enqueueWork {
            data?.type?.handler?.invoke(data)
        }
        context.get().packetHandled = true
    }
}