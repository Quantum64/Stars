@file:UseSerializers(BlockPosSerializer::class, ResourceLocationSerializer::class, SoundEventSerializer::class)

package co.q64.stars.capability

import co.q64.stars.type.*
import co.q64.stars.util.BlockPosSerializer
import co.q64.stars.util.ResourceLocationSerializer
import co.q64.stars.util.SoundEventSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos
import java.util.*

@Serializable
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
        var lastSeed: FormingBlockType = BlueFormingBlockType,
        var level: Level = RedLevel, // TODO
        var hubSpawn: BlockPos = BlockPos.ZERO,
        var hubEntryPosition: BlockPos = BlockPos.ZERO,
        var hubEntryDimension: ResourceLocation? = null,
        var tutorialEntryPosition: BlockPos = BlockPos.ZERO,
        var tutorialEntryDimension: ResourceLocation? = null,
        var nextSeeds: MutableList<FormingBlockType> = mutableListOf(),
        var lastSounds: Set<SoundEvent> = mutableSetOf(),
        var lastPlayed: MutableMap<SoundEvent, Long> = mutableMapOf()
) {
    //fun getLastPlayed(event: SoundEvent) = lastPlayed.getOrDefault(event, 0L)
    //fun updatePlayed(event: SoundEvent, time: Long) {
    //lastPlayed[event] = time
    // }
}