package co.q64.stars.dimension;

import com.google.auto.factory.AutoFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class StarsDimension extends Dimension {
    private static final Vec3d fogColor = new Vec3d(1, 1, 1);

    private ChunkGeneratorFactory generatorFactory;
    private World world;
    private Biome biome;

    protected StarsDimension(World world, DimensionType type, ChunkGeneratorFactory generatorFactory, Biome biome) {
        super(world, type);
        this.world = world;
        this.biome = biome;
        this.generatorFactory = generatorFactory;
    }

    @Override
    protected void generateLightBrightnessTable() {
        for (int i = 0; i < lightBrightnessTable.length; i++) {
            lightBrightnessTable[i] = 1.0f;
        }
    }

    public ChunkGenerator<?> createChunkGenerator() {
        return generatorFactory.create(world);
    }

    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
        return chunkPosIn.asBlockPos();
    }

    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        return new BlockPos(posX, 42, posZ);
    }

    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0;
    }

    public boolean isSurfaceWorld() {
        return false;
    }

    public boolean isSkyColored() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Vec3d getSkyColor(BlockPos cameraPos, float partialTicks) {
        return fogColor;
    }

    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return fogColor;
    }

    public boolean canRespawnHere() {
        return false;
    }

    public boolean doesXZShowFog(int x, int z) {
        return false;
    }

    public Biome getBiome(BlockPos pos) {
        return biome;
    }
}
