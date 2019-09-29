package co.q64.stars.dimension.overworld.placement;

import co.q64.stars.util.Identifiers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;
import java.util.stream.Stream;

@Singleton
public class GatewayPlacement extends SimplePlacement<NoPlacementConfig> {
    @Inject
    protected GatewayPlacement(Identifiers identifiers) {
        super(NoPlacementConfig::deserialize);
        setRegistryName(identifiers.get("gateway"));
    }

    protected Stream<BlockPos> getPositions(Random random, NoPlacementConfig config, BlockPos pos) {
        if (random.nextInt(1500) != 0) {
            return Stream.empty();
        }
        pos = new BlockPos(pos.getX(), 255, pos.getZ());
        return Stream.of(pos);
    }
}
