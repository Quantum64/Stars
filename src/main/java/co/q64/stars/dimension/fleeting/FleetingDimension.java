package co.q64.stars.dimension.fleeting;

import co.q64.stars.dimension.StarsDimension;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

@AutoFactory
public class FleetingDimension extends StarsDimension {
    protected FleetingDimension(World world, DimensionType type,
                                @Provided co.q64.stars.dimension.fleeting.FleetingChunkGeneratorFactory generatorFactory,
                                @Provided FleetingBiome fleetingBiome) {
        super(world, type, generatorFactory, fleetingBiome);
    }
}
