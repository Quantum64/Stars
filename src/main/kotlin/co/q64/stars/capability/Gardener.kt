package co.q64.stars.capability

import co.q64.stars.type.FleetingStage
import co.q64.stars.type.FormingBlockType
import co.q64.stars.type.Level
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos
import java.util.*

data class Gardener(
        var seeds: Int = 0,
        var keys: Int = 0,
        var seedVisibility: Int = 0,
        var seedsSincePink: Int = 0,
        var totalSeeds: Int = 0,
        var hubIndex: Int = 0,
        var lastJumped: Long = 0,
        var openDoor: Boolean = false,
        var openChallengeDoor: Boolean = false,
        var enteringHub: Boolean = false,
        var enteringFleeting: Boolean = false,
        var completeChallenge: Boolean = false,
        var tutorialMode: Boolean = false,
        var fleetingStage: FleetingStage = FleetingStage.NONE,
        var level: Level,
        var hubSpawn: BlockPos,
        var hubEntryPosition: BlockPos,
        var hubEntryDimension: ResourceLocation,
        var tutorialEntryPosition: BlockPos,
        var tutorialEntryDimension: ResourceLocation,
        var nextSeeds: Deque<FormingBlockType> = ArrayDeque(),
        var lastSounds: Set<SoundEvent> = mutableSetOf(),
        var lastPlayed: Map<SoundEvent, Long> = mutableMapOf()
)