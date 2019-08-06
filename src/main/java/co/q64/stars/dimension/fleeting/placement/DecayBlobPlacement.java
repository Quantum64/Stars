package co.q64.stars.dimension.fleeting.placement;

import co.q64.stars.util.Identifiers;
import co.q64.stars.util.SpawnpointManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;
import java.util.stream.Stream;

@Singleton
public class DecayBlobPlacement extends SimplePlacement<NoPlacementConfig> {
    private static final int MIN_SPAWN_DIST = 16;

    @Inject
    protected DecayBlobPlacement(Identifiers identifiers) {
        super(NoPlacementConfig::deserialize);
        setRegistryName(identifiers.get("decay_blob"));
    }

    public Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos) {
        BlockPos nearestIsland = new BlockPos(
                SpawnpointManager.SPREAD_DISTANCE * (Math.round(pos.getX() / Double.valueOf(SpawnpointManager.SPREAD_DISTANCE))),
                pos.getY(),
                SpawnpointManager.SPREAD_DISTANCE * (Math.round(pos.getZ() / Double.valueOf(SpawnpointManager.SPREAD_DISTANCE)))
        );
        boolean high = pos.distanceSq(nearestIsland) < MIN_SPAWN_DIST * MIN_SPAWN_DIST;
        int dist = (int) Math.sqrt(pos.distanceSq(nearestIsland)) - MIN_SPAWN_DIST;
        //int chance = 20 - (dist);
        //chance = chance < 1 ? 1 : chance;

        Stream<BlockPos> stream = Stream.empty();
        for (int i = 0; i < 10; i++) {
            //if (random.nextInt(chance) == 0) {
            stream = generate(stream, pos, random, high);
            //}
        }
        //if (dist > 40) {
        for (int i = 0; i < 10; i++) {
            stream = generate(stream, pos, random, high);
        }
        //}

        return stream;
    }

    private Stream<BlockPos> generate(Stream<BlockPos> stream, BlockPos pos, Random random, boolean high) {
        return Stream.concat(stream, Stream.of(pos.add(random.nextInt(16), high ? (130 + random.nextInt(100)) : (20 + random.nextInt(180)), random.nextInt(16))));
    }
}