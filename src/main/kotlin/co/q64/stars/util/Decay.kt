package co.q64.stars.util

import co.q64.stars.block.BaseDecayBlock
import co.q64.stars.block.SpecialDecayBlock
import co.q64.stars.directions
import net.minecraft.util.IStringSerializable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld


fun ServerWorld.activateDecay(pos: BlockPos) {
    for (direction in directions) {
        val target = pos.offset(direction)
        val state = world.getBlockState(target)
        if (state.block is BaseDecayBlock) {
            world.setBlockState(target, state.with(BaseDecayBlock.active, true))
        }
    }
}

fun World.isDecayBlock(pos: BlockPos) = getBlockState(pos) is BaseDecayBlock

fun IWorld.createSpecialDecay(pos: BlockPos, type: SpecialDecayType, notify: Boolean = true) =
        setBlockState(pos, SpecialDecayBlock.defaultState.with(BaseDecayBlock.type, type), if (notify) 3 else 2)

enum class SpecialDecayType : IStringSerializable {
    HEART, DOOR, CHALLENGE_DOOR, KEY;

    override fun getName() = name.toLowerCase()
}