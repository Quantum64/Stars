package co.q64.stars.dimension.fleeting;

import co.q64.stars.dimension.DimensionTemplate;
import co.q64.stars.util.Identifiers;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.BiFunction;

@AutoFactory
public class FleetingSolidDimension extends FleetingDimension {
    protected FleetingSolidDimension(World world, DimensionType type,
                                     @Provided co.q64.stars.dimension.fleeting.FleetingSolidChunkGeneratorFactory generatorFactory,
                                     @Provided FleetingSolidBiome fleetingBiome) {
        super(world, type, generatorFactory, fleetingBiome, new Vec3d(1, 1, 1));
    }

    @Singleton
    public static class FleetingSolidDimensionTemplate extends ModDimension implements DimensionTemplate {
        protected @Inject FleetingSolidDimensionFactory fleetingSolidDimensionFactory;
        private final @Getter ResourceLocation id;

        @Inject
        protected FleetingSolidDimensionTemplate(Identifiers identifiers) {
            this.id = identifiers.get("fleeting_solid");
            setRegistryName(id);
        }

        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return fleetingSolidDimensionFactory::create;
        }
    }
}
