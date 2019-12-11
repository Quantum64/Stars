package co.q64.stars.tile

import co.q64.stars.type.BlueFormingBlockType
import co.q64.stars.type.FormingBlockType
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.util.Direction
import kotlin.random.Random

private const val fruitChance = 3
private val directions = Direction.values()

class FormingTile(
        val type: FormingBlockType = BlueFormingBlockType,
        val first: Boolean = true,
        val hard: Boolean = false,
        val multiplier: Double = 1.0,
        var iterations: Int = 5,
        var direction: Direction = Direction.UP) : SyncTileEntity(formingTileType), ITickableTileEntity {
    val formTicks: Int
    var ready: Boolean = false
    var buildTime: Int = 0

    init {
        buildTime = type.buildTime
        if (type.buildTimeOffset > 0) {
            buildTime += Random.nextInt(-type.buildTimeOffset, type.buildTimeOffset)
        }
        /*
         if (formType == brownFormingBlockType && direction == Direction.DOWN) {
            buildTime = 250;
        }
        */
        buildTime = (buildTime * multiplier).toInt()
        formTicks = buildTime / 50
    }
}