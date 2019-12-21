package co.q64.stars.tile

import kotlinx.serialization.Serializable
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.util.Direction
import net.minecraft.world.server.ServerWorld

private val directions = Direction.values()

class DecayTile : SyncTileEntity(decayTileType), ITickableTileEntity {
    var data: Data = Data()

    @Serializable
    data class Data(
            var multiplier: Double = 1.0,
            var active: Boolean = false
    )

    init {
        sync(::data, Data.serializer())
    }

    override fun tick() {
        with(data) {
            world?.let { world ->
                if (world.isRemote && !active) return
                if (ticks == 0) {
                    (world as? ServerWorld)?.getClosestPlayer(pos.x.toDouble(), pos.z.toDouble(), 1000.0)?.let {
                        // TODO level type
                    }
                }
                var count = 0

            }
        }
    }

}