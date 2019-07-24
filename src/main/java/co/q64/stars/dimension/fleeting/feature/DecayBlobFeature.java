package co.q64.stars.dimension.fleeting.feature;

import co.q64.stars.block.DecayBlock;
import co.q64.stars.block.SpecialDecayEdgeBlock;
import co.q64.stars.util.DecayManager;
import co.q64.stars.util.DecayManager.SpecialDecayType;
import co.q64.stars.util.FleetingManager;
import co.q64.stars.util.Identifiers;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Singleton
public class DecayBlobFeature extends Feature<NoFeatureConfig> {
    private static final int BLOB_SIZE = 16;
    private static final Direction[] DIRECTIONS = Direction.values();

    protected @Inject DecayBlock decayBlock;
    protected @Inject SpecialDecayEdgeBlock specialDecayEdgeBlock;
    protected @Inject DecayManager decayManager;

    @Inject
    protected DecayBlobFeature(Identifiers identifiers) {
        super(NoFeatureConfig::deserialize);
        setRegistryName(identifiers.get("decay_blob"));
    }

    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        BlockPos nearestIsland = new BlockPos(
                FleetingManager.SPREAD_DISTANCE * (Math.round(pos.getX() / Double.valueOf(FleetingManager.SPREAD_DISTANCE))),
                FleetingManager.SPAWN_HEIGHT,
                FleetingManager.SPREAD_DISTANCE * (Math.round(pos.getZ() / Double.valueOf(FleetingManager.SPREAD_DISTANCE)))
        );
        int dist = (int) Math.sqrt(pos.distanceSq(nearestIsland));
        dist *= 15;
        dist = dist > 15000 ? 15000 : dist;

        decayManager.createSpecialDecay(world, pos, SpecialDecayType.KEY, false);
        List<BlockPos> placedBlocks = new ArrayList<>();
        placedBlocks.add(pos);
        for (int i = 0; i < dist; ++i) {
            BlockPos blockpos = placedBlocks.get(rand.nextInt(placedBlocks.size()));
            blockpos = blockpos.add(rand.nextInt(3) - 1, rand.nextInt(3) - 1, rand.nextInt(3) - 1);
            int j = 0;
            for (Direction direction : DIRECTIONS) {
                Block block = world.getBlockState(blockpos.offset(direction)).getBlock();
                if (block == decayBlock || block == specialDecayEdgeBlock) {
                    ++j;
                }
                if (j > 1) {
                    break;
                }
            }
            if (j == 1) {
                placedBlocks.add(blockpos);
                world.setBlockState(blockpos, decayBlock.getDefaultState(), 2);
            }
        }
        return true;
    }
}