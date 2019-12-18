package co.q64.stars.tile

import co.q64.stars.block.*
import net.minecraft.tileentity.TileEntityType
import net.minecraft.tileentity.TileEntityType.Builder
import java.util.function.Supplier

val formingTileType: TileEntityType<FormingTile> = Builder.create(Supplier { FormingTile() }, FormingBlock).build(null)
val decayTileType: TileEntityType<DecayTile> = Builder.create(Supplier { DecayTile() }, DecayBlock, DecaySolidBlock, AirDecayBlock, SpecialDecayBlock, SpecialDecaySolidBlock).build(null)
val seedTileType: TileEntityType<SeedTile> = Builder.create(Supplier { SeedTile() }, SeedBlock, HardSeedBlock).build(null)

val tileTypes by lazy {
    listOf(
            formingTileType,
            decayTileType,
            seedTileType
    )
}