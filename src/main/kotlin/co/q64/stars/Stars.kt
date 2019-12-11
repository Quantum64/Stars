package co.q64.stars

import co.q64.stars.client.startClient
import net.minecraft.util.math.BlockPos
import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.DimensionManager
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod

@Mod(modId)
class Stars() {
    init {
        DistExecutor.runWhenOn(Dist.CLIENT) {
            Runnable {
                startClient()
            }
        }
        start()
    }
}