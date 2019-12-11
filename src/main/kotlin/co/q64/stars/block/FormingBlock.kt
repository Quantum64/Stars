package co.q64.stars.block

import net.minecraft.block.BlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.IBlockReader

object FormingBlock : BaseBlock("forming_block") {
    override fun hasTileEntity(state: BlockState?) = true
    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity? {
        return super.createTileEntity(state, world)
    }
}