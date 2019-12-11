package co.q64.stars.tile

import co.q64.stars.block.FormingBlock
import net.minecraft.tileentity.TileEntityType
import net.minecraft.tileentity.TileEntityType.Builder
import java.util.function.Supplier

val formingTileType: TileEntityType<FormingTile> = Builder.create(Supplier { FormingTile() }, FormingBlock).build(null)

val tileTypes by lazy {
    listOf(
            formingTileType
    )
}