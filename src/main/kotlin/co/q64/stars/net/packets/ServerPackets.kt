package co.q64.stars.net.packets

import co.q64.stars.net.Packet
import co.q64.stars.net.PacketType
import kotlinx.serialization.Serializable

@Serializable
class PlantSeedPacket : Packet(PacketType.PLANT_SEED) {
    companion object {
        fun handle(packet: PlantSeedPacket) {

        }
    }
}

@Serializable
class LostPacket : Packet(PacketType.LOST) {
    companion object {
        fun handle(packet: LostPacket) {

        }
    }
}

@Serializable
data class UpdateJumpPacket(val jumping: Boolean) : Packet(PacketType.UPDATE_JUMP) {
    companion object {
        fun handle(packet: UpdateJumpPacket) {

        }
    }
}