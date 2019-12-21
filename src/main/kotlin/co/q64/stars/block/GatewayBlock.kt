package co.q64.stars.block

import co.q64.stars.type.Level
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.BooleanProperty
import net.minecraft.state.EnumProperty
import net.minecraft.state.StateContainer

object GatewayBlock : BaseBlock("gateway", hard) {
    val type = EnumProperty.create("type", Level::class.java)
    val complete = BooleanProperty.create("complete")

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) =
            super.fillStateContainer(builder.add(type).add(complete))
}