package co.q64.stars.tile

import co.q64.stars.type.BlueFormingBlockType
import co.q64.stars.type.FormingBlockType
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.ITickableTileEntity
import net.minecraft.util.Direction
import net.minecraft.util.math.MathHelper
import kotlin.random.Random

private const val fruitChance = 3
private val directions = Direction.values()

class FormingTile(
        var type: FormingBlockType = BlueFormingBlockType,
        val first: Boolean = true,
        val hard: Boolean = false,
        val multiplier: Double = 1.0,
        var iterations: Int = 5,
        var direction: Direction = Direction.UP) : SyncTileEntity(formingTileType), ITickableTileEntity {
    private val formTicks: Int
    var ready: Boolean = false
    var buildTime: Int = 0
    var placed: Long = System.currentTimeMillis()
    var ticks = 0

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
        if (first) {
            iterations = type.iterations(MathHelper.getPositionRandom(getPos()))
        }
    }

    override fun read(compound: CompoundNBT) {
        with(compound) {
            direction = Direction.valueOf(getString("direction"))
            type = FormingBlockType.fromId(getString("type"))
            iterations = getInt("iterations")
            placed = getLong("placed")
            ready = getBoolean("ready")
            buildTime = getInt("buildTime")
            super.read(this)
        }
    }

    override fun write(compound: CompoundNBT): CompoundNBT {
        return super.write(compound).apply {
            putString("direction", direction.name)
            putString("type", type.id)
            putInt("iterations", iterations)
            putLong("placed", placed)
            putBoolean("ready", ready)
            putInt("buildTime", buildTime)
        }
    }

    override fun tick() {
        
    }
}