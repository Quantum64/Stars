package co.q64.stars.dimension.hub;

import co.q64.stars.dimension.StarsDimension;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

@AutoFactory
public class HubDimension extends StarsDimension {
    protected HubDimension(World world, DimensionType type,
                           @Provided co.q64.stars.dimension.hub.HubChunkGeneratorFactory generatorFactory,
                           @Provided HubBiome fleetingBiome) {
        super(world, type, generatorFactory, fleetingBiome);
    }
}
