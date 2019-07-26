package co.q64.stars.dimension;

import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;

public interface ChunkGeneratorFactory {
    public ChunkGenerator<GenerationSettings> create(IWorld world);
}
