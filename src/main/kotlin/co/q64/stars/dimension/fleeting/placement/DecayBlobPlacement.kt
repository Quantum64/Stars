package co.q64.stars.dimension.fleeting.placement

import co.q64.stars.id
import co.q64.stars.util.SPREAD_DISTANCE
import net.minecraft.util.math.BlockPos
import net.minecraft.world.gen.placement.NoPlacementConfig
import net.minecraft.world.gen.placement.SimplePlacement
import java.util.*
import java.util.stream.Stream
import kotlin.math.roundToInt


object DecayBlobPlacement : SimplePlacement<NoPlacementConfig>(NoPlacementConfig::deserialize) {
    private const val MIN_SPAWN_DIST = 16

    init {
        id = "decay_blob"
    }

    override fun getPositions(random: Random, config: NoPlacementConfig?, pos: BlockPos): Stream<BlockPos>? {
        val nearestIsland = BlockPos(
                SPREAD_DISTANCE * (pos.x / SPREAD_DISTANCE.toDouble()).roundToInt(),
                pos.y,
                SPREAD_DISTANCE * (pos.z / SPREAD_DISTANCE.toDouble()).roundToInt()
        )
        val high: Boolean = pos.distanceSq(nearestIsland) < MIN_SPAWN_DIST * MIN_SPAWN_DIST
        //val dist: Int = sqrt(pos.distanceSq(nearestIsland)).toInt() - MIN_SPAWN_DIST
        var stream: Stream<BlockPos> = Stream.empty()
        for (i in 0..29) {
            stream = generate(stream, pos, random, high)
        }
        return stream
    }

    private fun generate(stream: Stream<BlockPos>, pos: BlockPos, random: Random, high: Boolean): Stream<BlockPos> {
        return Stream.concat(stream, Stream.of(pos.add(random.nextInt(16), if (high) 130 + random.nextInt(100) else 20 + random.nextInt(180), random.nextInt(16))))
    }
}