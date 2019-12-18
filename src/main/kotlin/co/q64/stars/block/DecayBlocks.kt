package co.q64.stars.block

import co.q64.stars.util.SpecialDecayType
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.material.Material
import net.minecraft.state.BooleanProperty
import net.minecraft.state.EnumProperty
import net.minecraft.state.StateContainer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader

private val properties = Block.Properties.create(Material.GLASS).hardnessAndResistance(-1f, 3600000f)

sealed class BaseDecayBlock(id: String) : BaseBlock(id, properties) {
    companion object {
        val active: BooleanProperty = BooleanProperty.create("active")
        val type: EnumProperty<SpecialDecayType> = EnumProperty.create("type", SpecialDecayType::class.java)
    }

    init {
        defaultState = defaultState.with(active, false)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) =
            super.fillStateContainer(builder.add(active))

    override fun hasTileEntity(state: BlockState): Boolean = state.get(active)
    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity? {
        return super.createTileEntity(state, world)
    }
}

sealed class PhantomDecayBlock(id: String) : BaseDecayBlock(id) {
    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape = VoxelShapes.empty()
}

object DecayBlock : PhantomDecayBlock("decay")
object DecaySolidBlock : BaseDecayBlock("decay_solid")
object AirDecayBlock : PhantomDecayBlock("air_decay")

object SpecialDecayBlock : PhantomDecayBlock("special_decay") {
    init {
        defaultState = defaultState.with(type, SpecialDecayType.HEART)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) =
            super.fillStateContainer(builder.add(type))
}

object SpecialDecaySolidBlock : PhantomDecayBlock("special_decay_solid") {
    init {
        defaultState = defaultState.with(type, SpecialDecayType.HEART)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) =
            super.fillStateContainer(builder.add(type))
}