package co.q64.stars.net

import co.q64.stars.id
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.fml.network.NetworkRegistry
import net.minecraftforge.fml.network.PacketDistributor

private const val protocolVersion = "2"
private val channel = NetworkRegistry.ChannelBuilder.named("main".id)
        .clientAcceptedVersions { it == ChannelHolder.protocolVersion || it == NetworkRegistry.ABSENT }
        .serverAcceptedVersions { it == ChannelHolder.protocolVersion || it == NetworkRegistry.ABSENT }
        .networkProtocolVersion { ChannelHolder.protocolVersion }
        .simpleChannel()

object ChannelHolder {
    fun register() {
        channel.registerMessage(0, PacketContainer::class.java, { packet, buffer ->
            packet.encode(buffer)
        }, { buffer ->
            PacketContainer<Any>(buffer)
        }, { packet, context ->
            packet.handle(context)
        })
    }


}

fun ServerPlayerEntity.sendPacket(packet: Packet) {
    channel.send(PacketDistributor.PLAYER.with { this }, packet)
}
