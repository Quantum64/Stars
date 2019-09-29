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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.BiFunction;

@AutoFactory
public class ChallengeDimension extends FleetingDimension {
    protected ChallengeDimension(World world, DimensionType type,
                                 @Provided co.q64.stars.dimension.fleeting.ChallengeChunkGeneratorFactory generatorFactory,
                                 @Provided ChallengeBiome challengeBiome) {
        super(world, type, generatorFactory, challengeBiome, new Vec3d(0, 0, 0));
    }

    @Singleton
    public static class ChallengeDimensionTemplate extends ModDimension implements DimensionTemplate {
        protected @Inject co.q64.stars.dimension.fleeting.ChallengeDimensionFactory challengeDimensionFactory;
        private final @Getter ResourceLocation id;

        @Inject
        protected ChallengeDimensionTemplate(Identifiers identifiers) {
            this.id = identifiers.get("challenge");
            setRegistryName(id);
        }

        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return challengeDimensionFactory::create;
        }
    }
}
