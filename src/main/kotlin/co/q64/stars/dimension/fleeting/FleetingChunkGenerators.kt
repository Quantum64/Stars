package co.q64.stars.dimension.fleeting

import net.minecraft.world.IWorld
import net.minecraft.world.biome.provider.BiomeProvider
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.Heightmap

sealed class FleetingChunkGeneratorBase(world: IWorld, biome: BiomeProvider) : ChunkGenerator<GenerationSettings>(world, biome, GenerationSettings()) {
    override fun generateSurface(chunk: IChunk) = Unit
    override fun getGroundHeight(): Int = 0
    override fun makeBase(world: IWorld, chunk: IChunk) = Unit
    override fun func_222529_a(x: Int, z: Int, type: Heightmap.Type?): Int = 0
}

class FleetingChunkGenerator(world: IWorld) : FleetingChunkGeneratorBase(world, FleetingBiome.provider)
class FleetingSolidChunkGenerator(world: IWorld) : FleetingChunkGeneratorBase(world, FleetingSolidBiome.provider)
class ChallengeChunkGenerator(world: IWorld) : FleetingChunkGeneratorBase(world, ChallengeBiome.provider)