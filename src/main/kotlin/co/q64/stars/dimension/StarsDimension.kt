package co.q64.stars.dimension

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.IWorld
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.dimension.Dimension
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationSettings
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn


abstract class StarsDimension(
        world: World,
        type: DimensionType,
        private val generatorFactory: (IWorld) -> ChunkGenerator<GenerationSettings>,
        private val biome: Biome,
        private val color: Vec3d) : Dimension(world, type) {

    override fun generateLightBrightnessTable() {
        lightBrightnessTable.indices.forEach {
            lightBrightnessTable[it] = 1.0f
        }
    }

    override fun createChunkGenerator(): ChunkGenerator<*> = generatorFactory(world)
    override fun findSpawn(chunkPosIn: ChunkPos, checkValid: Boolean): BlockPos = chunkPosIn.asBlockPos()
    override fun findSpawn(posX: Int, posZ: Int, checkValid: Boolean) = BlockPos(posX, 42, posZ)
    override fun calculateCelestialAngle(worldTime: Long, partialTicks: Float) = 0.0f
    override fun isSurfaceWorld() = false
    override fun isSkyColored() = false
    override fun getFogColor(celestialAngle: Float, partialTicks: Float) = color
    override fun canRespawnHere() = false
    override fun doesXZShowFog(x: Int, z: Int) = false
    override fun getBiome(pos: BlockPos?) = biome

    @OnlyIn(Dist.CLIENT)
    override fun getSkyColor(cameraPos: BlockPos?, partialTicks: Float) = color
}