package co.q64.stars.dimension.fleeting

import co.q64.stars.one
import co.q64.stars.dimension.StarsDimension
import co.q64.stars.id
import net.minecraft.world.World
import net.minecraft.world.dimension.Dimension
import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.common.ModDimension
import java.util.function.BiFunction

class FleetingDimension(world: World, type: DimensionType) : StarsDimension(world, type, ::FleetingChunkGenerator, FleetingBiome, one)
class FleetingSolidDimension(world: World, type: DimensionType) : StarsDimension(world, type, ::FleetingChunkGenerator, FleetingBiome, one)
class ChallengeDimension(world: World, type: DimensionType) : StarsDimension(world, type, ::FleetingChunkGenerator, FleetingBiome, one)

object FleetingDimensionTemplate : ModDimension() {
    init {
        id = "fleeting"
    }

    override fun getFactory(): BiFunction<World, DimensionType, out Dimension> =
            BiFunction { world, type -> FleetingDimension(world, type) }
}

object FleetingSolidDimensionTemplate : ModDimension() {
    init {
        id = "fleeting_solid"
    }

    override fun getFactory(): BiFunction<World, DimensionType, out Dimension> =
            BiFunction { world, type -> FleetingSolidDimension(world, type) }
}

object ChallengeDimensionTemplate : ModDimension() {
    init {
        id = "challenge"
    }

    override fun getFactory(): BiFunction<World, DimensionType, out Dimension> =
            BiFunction { world, type -> ChallengeDimension(world, type) }
}
