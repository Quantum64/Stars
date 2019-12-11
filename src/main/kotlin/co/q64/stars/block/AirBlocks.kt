package co.q64.stars.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.material.Material
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader

private val air = Block.Properties.create(Material.GLASS).hardnessAndResistance(-1f, 3600000f)

object DarkAirBlock : BaseBlock("dark_air", air) {
    override fun getRenderLayer() = BlockRenderLayer.TRANSLUCENT
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = VoxelShapes.empty()
    override fun isSideInvisible(state: BlockState, next: BlockState, side: Direction) = next.block is DarkAirBlock
}