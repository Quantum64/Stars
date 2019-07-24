package co.q64.stars.dimension.fleeting;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap.Type;

@AutoFactory
public class FleetingChunkGenerator extends ChunkGenerator<GenerationSettings> {
    protected FleetingChunkGenerator(IWorld world, @Provided FleetingBiomeProvider biomeProvider) {
        super(world, biomeProvider.getProvider(), new GenerationSettings());
    }

    public void generateSurface(IChunk p_222535_1_) {
    }

    public int getGroundHeight() {
        return 0;
    }

    public void makeBase(IWorld p_222537_1_, IChunk p_222537_2_) {
    }

    public int func_222529_a(int p_222529_1_, int p_222529_2_, Type p_222529_3_) {
        return 0;
    }
}
