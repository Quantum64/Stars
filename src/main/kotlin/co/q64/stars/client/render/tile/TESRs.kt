package co.q64.stars.client.render.tile

import co.q64.stars.tile.FormingTile
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.tileentity.TileEntity
import kotlin.reflect.KClass

data class TESRDefinition<T : TileEntity>(val type: KClass<T>, val render: TileEntityRenderer<T>)

val tesrs by lazy {
    listOf(
            TESRDefinition(FormingTile::class, FormingBlockRender)
    )
}