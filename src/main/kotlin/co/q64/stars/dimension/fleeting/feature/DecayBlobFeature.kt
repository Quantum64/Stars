package co.q64.stars.dimension.fleeting.feature

import co.q64.stars.block.DecayBlock
import co.q64.stars.block.DecaySolidBlock
import co.q64.stars.block.SpecialDecayBlock
import co.q64.stars.id
import co.q64.stars.util.SPREAD_DISTANCE
import co.q64.stars.util.SpecialDecayType
import co.q64.stars.util.createSpecialDecay
import net.minecraft.block.Block
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import java.util.*
import java.util.function.Function
import kotlin.math.roundToInt
import kotlin.math.sqrt


private const val BLOB_SIZE = 16
private val DIRECTIONS = Direction.values()

sealed class DecayBlobFeatureBase(private val block: Block) : Feature<NoFeatureConfig>(Function { NoFeatureConfig.deserialize(it) }) {
    override fun place(world: IWorld, generator: ChunkGenerator<out GenerationSettings?>?, rand: Random, pos: BlockPos, config: NoFeatureConfig?): Boolean {
        val nearestIsland = BlockPos(
                SPREAD_DISTANCE * (pos.x / SPREAD_DISTANCE.toDouble()).roundToInt(),
                pos.y,
                SPREAD_DISTANCE * (pos.z / SPREAD_DISTANCE.toDouble()).roundToInt()
        )
        var dist = sqrt(pos.distanceSq(nearestIsland)).toInt()
        if (dist < 35) {
            dist = rand.nextInt(dist + 1)
            if (dist < 16) {
                if (pos.y < 115) {
                    return false
                }
            }
        } else {
            dist = dist * dist / 3
            dist = if (dist > 5000) 5000 else dist
        }
        createSpecialDecay(world, pos, if (rand.nextInt(10) == 0) SpecialDecayType.CHALLENGE_DOOR else SpecialDecayType.KEY, false)
        val placedBlocks: MutableList<BlockPos> = ArrayList()
        placedBlocks.add(pos)
        for (i in 0 until dist) {
            var blockpos = placedBlocks[rand.nextInt(placedBlocks.size)]
            blockpos = blockpos.add(rand.nextInt(3) - 1, rand.nextInt(3) - 1, rand.nextInt(3) - 1)
            var j = 0
            for (direction in DIRECTIONS) {
                val block = world.getBlockState(blockpos.offset(direction)).block
                if (block == block || block == SpecialDecayBlock) {
                    ++j
                }
                if (j > 1) {
                    break
                }
            }
            if (j == 1) {
                placedBlocks.add(blockpos)
                world.setBlockState(blockpos, block.defaultState, 2)
            }
        }
        return true
    }
}

object DecayBlobFeature : DecayBlobFeatureBase(DecayBlock) {
    init {
        id = "decay_blob"
    }
}

object SolidDecayBlobFeature : DecayBlobFeatureBase(DecaySolidBlock) {
    init {
        id = "solid_decay_blob"
    }
}