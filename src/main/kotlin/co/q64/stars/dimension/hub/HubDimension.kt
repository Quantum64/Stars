package co.q64.stars.dimension.hub

import co.q64.stars.one
import co.q64.stars.dimension.StarsDimension
import co.q64.stars.id
import net.minecraft.world.World
import net.minecraft.world.dimension.Dimension
import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.common.ModDimension
import java.util.function.BiFunction

class HubDimension(world: World, type: DimensionType) : StarsDimension(world, type, ::HubChunkGenerator, HubBiome, one)

object HubDimensionTemplate : ModDimension() {
    init {
        id = "hub"
    }

    override fun getFactory(): BiFunction<World, DimensionType, out Dimension> =
            BiFunction { world, type -> HubDimension(world, type) }
}