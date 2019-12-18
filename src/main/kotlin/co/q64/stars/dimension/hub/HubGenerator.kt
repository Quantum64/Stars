package co.q64.stars.dimension.hub

import net.minecraft.world.IWorld
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.Heightmap.Type


class HubChunkGenerator(world: IWorld) : ChunkGenerator<GenerationSettings>(world, HubBiome.provider, GenerationSettings()) {
    override fun generateSurface(chunk: IChunk) = Unit
    override fun getGroundHeight(): Int = 0
    override fun makeBase(world: IWorld, chunk: IChunk) = Unit
    override fun func_222529_a(x: Int, z: Int, type: Type?): Int = 0
}