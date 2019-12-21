package co.q64.stars.block

import net.minecraft.state.BooleanProperty

object DarknessBlock : BaseBlock("darkness", hard) {
    val active: BooleanProperty = BooleanProperty.create("active")
}