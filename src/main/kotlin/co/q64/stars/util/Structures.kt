package co.q64.stars.util

import co.q64.stars.block.*
import co.q64.stars.id
import co.q64.stars.type.Level
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.nbt.CompressedStreamTools
import net.minecraft.profiler.IProfiler
import net.minecraft.resources.IFutureReloadListener
import net.minecraft.resources.IFutureReloadListener.IStage
import net.minecraft.resources.IResourceManager
import net.minecraft.util.math.BlockPos
import java.io.IOException
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.function.Consumer
import java.util.function.Supplier
import kotlin.experimental.and


private var structures = mapOf<StructureType, Structure>()

enum class StructureType(val id: String) {
    HUB_WHITE("hub_white.dat"),
    HUB_GREEN("hub_green.dat"),
    HUB_ORANGE("hub_orange.dat"),
    HUB_BLUE("hub_blue.dat"),
    HUB_PURPLE("hub_purple.dat"),
    HUB_YELLOW("hub_yellow.dat"),
    HUB_TEAL("hub_teal.dat"),
    HUB_RED("hub_red.dat"),
    HUB_PINK("hub_pink.dat"),
    HUB_CYAN("hub_cyan.dat"),

    CHALLENGE_WHITE("challenge_white.dat"),
    CHALLENGE_GREEN("challenge_green.dat"),
    CHALLENGE_ORANGE("challenge_orange.dat"),
    CHALLENGE_BLUE("challenge_blue.dat"),
    CHALLENGE_PURPLE("challenge_purple.dat"),
    CHALLENGE_YELLOW("challenge_yellow.dat"),
    CHALLENGE_TEAL("challenge_teal.dat"),
    CHALLENGE_RED("challenge_red.dat"),
    CHALLENGE_PINK("challenge_pink.dat"),
    CHALLENGE_CYAN("challenge_cyan.dat");

    val structure: Structure
        get() = structures[this] ?: error("Invalid structure access")
}

object StructureLoader : IFutureReloadListener {
    override fun reload(stage: IStage, resourceManager: IResourceManager, backgroundProfiler: IProfiler?, gameProfiler: IProfiler?, backgroundExecutor: Executor, gameExecutor: Executor): CompletableFuture<Void> {
        val future: CompletableFuture<Map<StructureType, Structure>> =
                CompletableFuture.supplyAsync(Supplier {
                    this.prepare(resourceManager, backgroundProfiler)
                }, backgroundExecutor)
        stage.javaClass // TODO ??
        return future.thenCompose { stage.markCompleteAwaitingOthers(it) }
                .thenAcceptAsync(Consumer { map: Map<StructureType, Structure> ->
                    apply(map, resourceManager, gameProfiler)
                }, gameExecutor)
    }

    private fun prepare(resourceManager: IResourceManager, profiler: IProfiler?): Map<StructureType, Structure> {
        val result: MutableMap<StructureType, Structure> = EnumMap(StructureType::class.java)
        for (type in StructureType.values()) {
            try {
                resourceManager.getResource("world/${type.id}".id).use { resource ->
                    val tag = CompressedStreamTools.readCompressed(resource.inputStream)
                    val width = tag.getShort("Width").toInt()
                    val height = tag.getShort("Height").toInt()
                    val length = tag.getShort("Length").toInt()
                    val blocks = tag.getByteArray("Blocks")
                    val data = tag.getByteArray("Data")
                    result.put(type, Structure(blocks, data, width, length, height))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }

    fun apply(new: Map<StructureType, Structure>, resourceManager: IResourceManager, profiler: IProfiler?) {
        structures = new
    }
}

data class Structure(
        private val blocks: ByteArray,
        private val data: ByteArray,
        val width: Int,
        val length: Int,
        val height: Int,
        val challengeStart: BlockPos? = null
) {
    private fun index(pos: BlockPos): Int {
        return (pos.y * length + pos.z) * width + pos.x
    }

    private fun getBlock(pos: BlockPos): BlockState {
        return ids.getOrDefault(index(
                (blocks[index(pos)] and 0xff.toByte()).toInt(),
                (data[index(pos)]).toInt()),
                Blocks.BEDROCK.defaultState)
    }

    fun forEachBlock(action: (BlockPos, BlockState) -> Unit, offset: BlockPos = BlockPos.ZERO) {
        for (x in 0 until width) {
            for (z in 0 until length) {
                for (y in 0 until height) {
                    val pos = BlockPos(x, y, z)
                    action(pos.add(offset), getBlock(pos))
                }
            }
        }
    }
}

private fun index(block: Int, color: Int): Int {
    return (block shl 4) + color
}

private enum class Block(val id: Int) {
    AIR(0),
    STONE(1),
    DIRT(3),
    COBBLESTONE(4),
    BEDROCK(7),
    WOOL(35),
    BARRIER(166),
    LAPIS(22),
    SPONGE(19),
    STAINED_GLASS(95),
    STAINED_CLAY(159);

    fun key(color: Color = Color.WHITE): Int = index(id, color.id)
}

private enum class Color(val id: Int) {
    WHITE(0),
    ORANGE(1),
    MAGENTA(2),
    LIGHT_BLUE(3),
    YELLOW(4),
    LIME(5),
    PINK(6),
    GRAY(7),
    LIGHT_GRAY(8),
    CYAN(9),
    PURPLE(10),
    BLUE(11),
    BROWN(12),
    GREEN(13),
    RED(14),
    BLACK(15);
}

val ids = mapOf<Int, BlockState>(
        Block.STAINED_CLAY.key(Color.PINK) to PinkFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.RED) to RedFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.MAGENTA) to RedPrimedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.BLUE) to BlueFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.ORANGE) to OrangeFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.PURPLE) to PurpleFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.BROWN) to BrownFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.GREEN) to GreenFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.YELLOW) to YellowFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.CYAN) to CyanFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.GRAY) to GreyFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.LIGHT_BLUE) to TealFormedBlock.defaultState,
        Block.STAINED_CLAY.key(Color.WHITE) to WhiteFormedBlock.defaultState,

        Block.WOOL.key(Color.RED) to RedFormedBlockHard.defaultState,
        Block.WOOL.key(Color.MAGENTA) to RedPrimedBlockHard.defaultState,
        Block.WOOL.key(Color.BLUE) to BlueFormedBlockHard.defaultState,
        Block.WOOL.key(Color.ORANGE) to OrangeFormedBlockHard.defaultState,
        Block.WOOL.key(Color.PURPLE) to PurpleFormedBlockHard.defaultState,
        Block.WOOL.key(Color.BROWN) to BrownFormedBlockHard.defaultState,
        Block.WOOL.key(Color.GREEN) to GreenFormedBlockHard.defaultState,
        Block.WOOL.key(Color.YELLOW) to YellowFormedBlockHard.defaultState,
        Block.WOOL.key(Color.CYAN) to CyanFormedBlockHard.defaultState,

        Block.STAINED_GLASS.key(Color.WHITE) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),
        Block.STAINED_GLASS.key(Color.RED) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),
        Block.STAINED_GLASS.key(Color.ORANGE) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),
        Block.STAINED_GLASS.key(Color.BLUE) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),
        Block.STAINED_GLASS.key(Color.CYAN) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),
        Block.STAINED_GLASS.key(Color.GREEN) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),
        Block.STAINED_GLASS.key(Color.PINK) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),
        Block.STAINED_GLASS.key(Color.PURPLE) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),
        Block.STAINED_GLASS.key(Color.YELLOW) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),
        Block.STAINED_GLASS.key(Color.LIGHT_BLUE) to GatewayBlock.defaultState.with(GatewayBlock.type, Level.RED),

        Block.BARRIER.key() to Blocks.BARRIER.defaultState,
        Block.STONE.key() to DecayBlock.defaultState,
        Block.DIRT.key() to DarknessBlock.defaultState,
        Block.BEDROCK.key() to DecaySolidBlock.defaultState,
        Block.COBBLESTONE.key() to DecayBlock.defaultState.with(BaseDecayBlock.active, true)
)