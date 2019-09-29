package co.q64.stars.dimension.fleeting;

import co.q64.stars.dimension.ChunkGeneratorFactory;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap.Type;

@AutoFactory(implementing = ChunkGeneratorFactory.class)
public class ChallengeChunkGenerator extends ChunkGenerator<GenerationSettings> {
    protected ChallengeChunkGenerator(IWorld world, @Provided ChallengeBiomeProvider biomeProvider) {
        super(world, biomeProvider.getProvider(), new GenerationSettings());
    }

    public void generateSurface(IChunk chunk) {}

    public int getGroundHeight() {
        return 0;
    }

    public void makeBase(IWorld world, IChunk chunk) {}

    public int func_222529_a(int x, int z, Type type) {
        return 0;
    }
}