package co.q64.stars.dimension

import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.common.ModDimension

val ModDimension.type: DimensionType
    get() = DimensionType.byName(registryName!!)!! // TODO lol