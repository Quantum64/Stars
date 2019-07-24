package co.q64.stars.dimension.fleeting.placement;

import co.q64.stars.util.FleetingManager;
import co.q64.stars.util.Identifiers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;
import java.util.stream.Stream;

@Singleton
public class DecayBlobPlacement extends SimplePlacement<NoPlacementConfig> {
    private static final int MIN_SPAWN_DIST = 20;

    @Inject
    protected DecayBlobPlacement(Identifiers identifiers) {
        super(NoPlacementConfig::deserialize);
        setRegistryName(identifiers.get("decay_blob"));
    }

    public Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos) {
        BlockPos nearestIsland = new BlockPos(
                FleetingManager.SPREAD_DISTANCE * (Math.round(pos.getX() / Double.valueOf(FleetingManager.SPREAD_DISTANCE))),
                FleetingManager.SPAWN_HEIGHT,
                FleetingManager.SPREAD_DISTANCE * (Math.round(pos.getZ() / Double.valueOf(FleetingManager.SPREAD_DISTANCE)))
        );
        if (pos.distanceSq(nearestIsland) < MIN_SPAWN_DIST * MIN_SPAWN_DIST ||
                pos.distanceSq(nearestIsland.add(0, -FleetingManager.SPAWN_HEIGHT / 2, 0)) < MIN_SPAWN_DIST * MIN_SPAWN_DIST) {
            return Stream.empty();
        }
        int dist = (int) Math.sqrt(pos.distanceSq(nearestIsland)) - MIN_SPAWN_DIST;
        int chance = 33 - (dist / 3);
        chance = chance < 1 ? 1 : chance;

        Stream<BlockPos> stream = Stream.empty();
        for (int i = 0; i < 3; i++) {
            if (random.nextInt(chance) == 0) {
                stream = generate(stream, pos, random);
            }
        }
        if (dist > 130) {
            for (int i = 0; i < 5; i++) {
                stream = generate(stream, pos, random);
            }
        }

        return stream;
    }

    private Stream<BlockPos> generate(Stream<BlockPos> stream, BlockPos pos, Random random) {
        return Stream.concat(stream, Stream.of(pos.add(random.nextInt(16), 20 + random.nextInt(150), random.nextInt(16))));
    }
}