package co.q64.stars.util

import co.q64.stars.block.DecayBlock
import co.q64.stars.block.OrangeFormedBlock
import co.q64.stars.block.OrangeFormedBlockHard
import co.q64.stars.dimension.fleeting.ChallengeDimensionTemplate
import co.q64.stars.dimension.fleeting.FleetingDimensionTemplate
import co.q64.stars.dimension.fleeting.FleetingSolidDimensionTemplate
import co.q64.stars.dimension.type
import co.q64.stars.tile.DecayTile
import co.q64.stars.type.Level
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.potion.Effects
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.DimensionManager
import sun.audio.AudioPlayer.player
import java.util.*


private val levitationQueue: MutableMap<UUID, Int> = mutableMapOf()


fun ServerPlayerEntity.starsTick() {
    levitationQueue[uniqueID]?.let { ticks ->
        if (ticks > 0) {
            levitationQueue[uniqueID] = ticks
            return
        }
        levitationQueue.remove(uniqueID)
        playerManager.useSeed(player, {
            addPotionEffect(EffectInstance(Effects.LEVITATION, 200, 1, true, false))
            //sounds.playSound(player.getServerWorld(), player.getPosition(), bubbleSound, 1f)
        })
    }
}

private val ServerPlayerEntity.fleetingWorld: World
    get() = with(gardener) {
        DimensionManager.getWorld(server, when {
            openChallengeDoor -> ChallengeDimensionTemplate.type
            level == Level.TEAL -> FleetingSolidDimensionTemplate.type
            else -> FleetingDimensionTemplate.type
        }, false, true)!!
    }


private fun ServerPlayerEntity.setupFleeting(world: World, pos: BlockPos, level: Level) {
    for (y in pos.y - 8 until pos.y) {
        for (x in pos.x - 2..pos.x + 2) {
            for (z in pos.z - 2..pos.z + 2) {
                world.setBlockState(BlockPos(x, y, z),
                        if (gardener.level === Level.PURPLE) OrangeFormedBlockHard.defaultState
                        else OrangeFormedBlock.defaultState)
            }
        }
    }
    val door = BlockPos(pos.x, pos.y - 8, pos.z)
    world.createSpecialDecay(door, SpecialDecayType.DOOR)
    (world.getTileEntity(door) as? DecayTile)?.data?.let { tile ->
        if (gardener.level == Level.WHITE) {
            tile.multiplier = 1.25
        } else if (gardener.level == Level.ORANGE) {
            tile.multiplier = 0.5
        }
    }
}

fun World.createKey(key: BlockPos) {
    for (x in -3..2) {
        for (y in -3..2) {
            for (z in -3..2) {
                world.setBlockState(key.add(x, y, z), DecayBlock.defaultState)
            }
        }
    }
    world.createSpecialDecay(key, SpecialDecayType.KEY)
}