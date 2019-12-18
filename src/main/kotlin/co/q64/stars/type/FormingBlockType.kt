package co.q64.stars.type

import co.q64.stars.block.BlueFormedBlock
import co.q64.stars.block.BlueFormedBlockHard
import co.q64.stars.block.DarkAirBlock
import co.q64.stars.item.BlueRobustSeedItem
import co.q64.stars.item.BlueSeedItem
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.util.Direction
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld

sealed class FormingBlockType(
        val red: Int = 0,
        val green: Int = 0,
        val blue: Int = 0,
        val buildTime: Int,
        val formed: Block,
        val formedHard: Block,
        val id: String,
        val buildTimeOffset: Int = 0,
        val sounds: Set<SoundEvent> = setOf(),
        val seed: Item,
        val seedRobust: Item
) {
    fun hasBlock(world: World, pos: BlockPos, direction: Direction): Boolean {
        world.getBlockState(pos.offset(direction)).block.let {
            return it !== Blocks.AIR && it !is DarkAirBlock
        }
    }

    open fun iterations(seed: Long) = 0
    open fun decayTime(seed: Long) = 100
    open fun canGrow(): Boolean = true
    open fun firstDirection(world: World, position: BlockPos): Direction? = Direction.UP
    open fun nextDirections(world: World, position: BlockPos, last: Direction, iterations: Int): List<Direction> = listOf()

    companion object {
        fun fromId(id: String) = types.find { it.id == id } ?: BlueFormingBlockType
    }
}


object BlueFormingBlockType : FormingBlockType(
        id = "blue",
        buildTime = 4500,
        green = 114,
        blue = 255,
        formed = BlueFormedBlock,
        formedHard = BlueFormedBlockHard,
        seed = BlueSeedItem,
        seedRobust = BlueRobustSeedItem
) {

}

object RedFormingBlockType : FormingBlockType(
        id = "red",
        buildTime = 4500,
        green = 114,
        blue = 255,
        formed = BlueFormedBlock,
        formedHard = BlueFormedBlockHard,
        seed = BlueSeedItem,
        seedRobust = BlueRobustSeedItem
) {
    fun explode(world: ServerWorld, pos: BlockPos, decay: Boolean) {

    }
}

val types by lazy {
    listOf(
            BlueFormingBlockType
    )
}