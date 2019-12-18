package co.q64.stars.block

import co.q64.stars.tile.DecayingTile
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.world.IBlockReader

private val earth = Block.Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f)

object DecayingBlock : BaseBlock("decaying", earth) {
    override fun getRenderLayer() = BlockRenderLayer.CUTOUT
    override fun hasTileEntity(state: BlockState) = true
    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity = DecayingTile()
}