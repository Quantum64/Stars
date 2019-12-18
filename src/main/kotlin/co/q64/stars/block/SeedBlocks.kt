package co.q64.stars.block

import co.q64.stars.tile.SeedTile
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.world.IBlockReader

private val earth = Block.Properties.create(Material.EARTH).sound(SoundType.GROUND).hardnessAndResistance(0f, 0f)
private val hard = Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(-1f, 3600000f)

sealed class BaseSeedBlock(id: String, properties: Properties = earth) : BaseBlock(id, properties) {
    override fun getRenderLayer() = BlockRenderLayer.CUTOUT
    override fun hasTileEntity(state: BlockState) = true
    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity = SeedTile()
}

object SeedBlock : BaseSeedBlock("seed")
object HardSeedBlock : BaseSeedBlock("seed_hard", properties = hard), HardBlock