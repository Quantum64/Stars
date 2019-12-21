package co.q64.stars.net.packets

import co.q64.stars.capability.Gardener
import co.q64.stars.net.Packet
import co.q64.stars.net.PacketType
import co.q64.stars.util.gardenerClient
import kotlinx.serialization.Serializable

@Serializable
data class SyncCapabilityPacket(val capability: Gardener) : Packet(PacketType.SYNC_CAPABILITY) {
    companion object {
        fun handle(packet: SyncCapabilityPacket) {
            gardenerClient = packet.capability
        }
    }
}

@Serializable
data class FadeScreenPacket(val mode: FadeMode, val time: Long) : Packet(PacketType.FADE_SCREEN) {
    enum class FadeMode { // TODO Move
        TO_WHITE, FROM_WHITE, TO_BLACK, FROM_BLACK
    }

    companion object {
        fun handle(packet: FadeScreenPacket) {

        }
    }
}