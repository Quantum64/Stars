package co.q64.stars.net

import co.q64.stars.net.packets.*
import kotlinx.serialization.KSerializer


enum class PacketType(val serializer: KSerializer<*>, val handler: (Any) -> Unit) {
    // Client -> Server
    PLANT_SEED(PlantSeedPacket.serializer(), { if (it is PlantSeedPacket) PlantSeedPacket.handle(it) }),
    LOST(LostPacket.serializer(), { if (it is LostPacket) LostPacket.handle(it) }),
    UPDATE_JUMP(UpdateJumpPacket.serializer(), { if (it is UpdateJumpPacket) UpdateJumpPacket.handle(it) }),

    // Server -> Client
    FADE_SCREEN(FadeScreenPacket.serializer(), { if (it is FadeScreenPacket) FadeScreenPacket.handle(it) }),
    SYNC_CAPABILITY(SyncCapabilityPacket.serializer(), { if (it is SyncCapabilityPacket) SyncCapabilityPacket.handle(it) })
}