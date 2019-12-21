package co.q64.stars.util

import co.q64.stars.dimension.hub.HubDimension
import co.q64.stars.net.packets.SyncCapabilityPacket
import co.q64.stars.net.sendPacket
import co.q64.stars.type.*
import net.minecraft.entity.player.ServerPlayerEntity
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

private val toSync: Queue<ServerPlayerEntity> = ConcurrentLinkedQueue()

var ServerPlayerEntity.seeds: Int
    get() = gardener.seeds
    set(value) {
        gardener.seeds = value
        sync()
    }

fun ServerPlayerEntity.updateSeeds() = with(gardener) {
    val hub = serverWorld.dimension is HubDimension || enteringHub || openChallengeDoor || openDoor
    if (fleetingStage == FleetingStage.LIGHT || hub) {
        val types = if (hub) hubFormingTypes else fleetingFormingTypes
        while (nextSeeds.size < seedVisibility && seeds > 0) {
            var offering: FormingBlockType = PinkFormingBlockType
            if (hub || seedsSincePink < 5 + (0..1).random()) {
                for (i in 0..50) {
                    offering = types.random()
                    if (offering != lastSeed) break
                }
            }
            if (!hub && level == Level.CYAN) {
                if ((0..3).random() == 0) {
                    offering = CyanFormingBlockType
                }
            }
            if (!hub && fleetingStage == FleetingStage.LIGHT) {
                if (level == Level.CYAN) {
                    when (totalSeeds) {
                        0 -> offering = CyanFormingBlockType
                        1 -> offering = PinkFormingBlockType
                        2 -> {
                            offering = YellowFormingBlockType
                            seedsSincePink = 0
                        }
                    }
                } else {
                    when (totalSeeds) {
                        0 -> offering = PinkFormingBlockType
                        1 -> offering = YellowFormingBlockType
                        2 -> offering = GreenFormingBlockType
                    }
                }
            }
            seedsSincePink = if (offering == PinkFormingBlockType) 0 else seedsSincePink + 1
            lastSeed = offering
            nextSeeds.add(offering)
            totalSeeds++
            seeds--
        }
    }
    sync()
}

fun ServerPlayerEntity.useSeed(callback: () -> Unit) {
    if (seeds > 0) {
        seeds--
        callback()
    }
}

fun ServerPlayerEntity.addSeed(sound: Boolean = false) {
    seeds++
    updateSeeds()
    if (sound) {

    }
}

fun ServerPlayerEntity.sync() {
    if (this in toSync) return
    toSync.add(this)
}

fun processCapabilitySync() {
    while (true) {
        val player = toSync.poll() ?: break
        player.sendPacket(SyncCapabilityPacket(player.gardener))
    }
}