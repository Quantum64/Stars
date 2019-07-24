package co.q64.stars.dimension.hub;

import co.q64.stars.dimension.fleeting.FleetingBiome;
import co.q64.stars.dimension.fleeting.FleetingChunkGeneratorFactory;
import co.q64.stars.dimension.fleeting.FleetingDimension;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

@AutoFactory
public class HubDimension extends FleetingDimension {
    private static final Vec3d fogColor = new Vec3d(1, 1, 1);


    protected HubDimension(World world, DimensionType type,
                           @Provided co.q64.stars.dimension.fleeting.FleetingChunkGeneratorFactory generatorFactory,
                           @Provided FleetingBiome fleetingBiome) {
        super(world, type, generatorFactory, fleetingBiome);
    }
}
