package co.q64.stars.dimension.fleeting;

import co.q64.stars.dimension.ChunkGeneratorFactory;
import co.q64.stars.dimension.DimensionTemplate;
import co.q64.stars.dimension.StarsDimension;
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
public class FleetingDimension extends StarsDimension {
    protected FleetingDimension(World world, DimensionType type,
                                @Provided co.q64.stars.dimension.fleeting.FleetingChunkGeneratorFactory generatorFactory,
                                @Provided FleetingBiome fleetingBiome) {
        super(world, type, generatorFactory, fleetingBiome, new Vec3d(1, 1, 1));
    }

    protected FleetingDimension(World world, DimensionType type, ChunkGeneratorFactory generatorFactory, FleetingBiome fleetingBiome, Vec3d color) {
        super(world, type, generatorFactory, fleetingBiome, color);
    }

    @Singleton
    public static class FleetingDimensionTemplate extends ModDimension implements DimensionTemplate {
        protected @Inject FleetingDimensionFactory fleetingDimensionFactory;
        private final @Getter ResourceLocation id;

        @Inject
        protected FleetingDimensionTemplate(Identifiers identifiers) {
            this.id = identifiers.get("fleeting");
            setRegistryName(id);
        }

        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return fleetingDimensionFactory::create;
        }
    }
}
