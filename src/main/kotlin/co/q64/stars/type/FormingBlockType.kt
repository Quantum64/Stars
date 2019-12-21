package co.q64.stars.type

import co.q64.stars.block.BlueFormedBlock
import co.q64.stars.block.BlueFormedBlockHard
import co.q64.stars.block.DarkAirBlock
import co.q64.stars.item.BlueRobustSeedItem
import co.q64.stars.item.BlueSeedItem
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
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

    @Serializer(FormingBlockType::class)
    companion object : KSerializer<FormingBlockType> {
        override val descriptor: SerialDescriptor = StringDescriptor.withName("FormingBlockType")
        override fun serialize(encoder: Encoder, obj: FormingBlockType) = encoder.encodeString(obj.id)
        override fun deserialize(decoder: Decoder): FormingBlockType = fromId(decoder.decodeString())

        fun fromId(id: String) = formingTypes.find { it.id == id } ?: BlueFormingBlockType
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

object PinkFormingBlockType : FormingBlockType(
        id = "pink",
        buildTime = 4500,
        green = 114,
        blue = 255,
        formed = BlueFormedBlock,
        formedHard = BlueFormedBlockHard,
        seed = BlueSeedItem,
        seedRobust = BlueRobustSeedItem
) {
}

object CyanFormingBlockType : FormingBlockType(
        id = "cyan",
        buildTime = 4500,
        green = 114,
        blue = 255,
        formed = BlueFormedBlock,
        formedHard = BlueFormedBlockHard,
        seed = BlueSeedItem,
        seedRobust = BlueRobustSeedItem
) {

}

object YellowFormingBlockType : FormingBlockType(
        id = "cyan",
        buildTime = 4500,
        green = 114,
        blue = 255,
        formed = BlueFormedBlock,
        formedHard = BlueFormedBlockHard,
        seed = BlueSeedItem,
        seedRobust = BlueRobustSeedItem
) {

}

object GreenFormingBlockType : FormingBlockType(
        id = "cyan",
        buildTime = 4500,
        green = 114,
        blue = 255,
        formed = BlueFormedBlock,
        formedHard = BlueFormedBlockHard,
        seed = BlueSeedItem,
        seedRobust = BlueRobustSeedItem
) {

}

val formingTypes: List<FormingBlockType> by lazy {
    listOf(
            BlueFormingBlockType
    )
}

val fleetingFormingTypes by lazy {
    formingTypes.filter { it.canGrow() }
}

val hubFormingTypes by lazy {
    fleetingFormingTypes.filter { it !is PinkFormingBlockType }
}