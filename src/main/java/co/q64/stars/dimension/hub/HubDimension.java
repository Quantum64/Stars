package co.q64.stars.dimension.hub;

import co.q64.stars.dimension.DimensionTemplate;
import co.q64.stars.dimension.StarsDimension;
import co.q64.stars.util.Identifiers;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.BiFunction;

@AutoFactory
public class HubDimension extends StarsDimension {
    protected HubDimension(World world, DimensionType type,
                           @Provided co.q64.stars.dimension.hub.HubChunkGeneratorFactory generatorFactory,
                           @Provided HubBiome hubBiome) {
        super(world, type, generatorFactory, hubBiome);
    }

    @Singleton
    public static class HubDimensionTemplate extends ModDimension implements DimensionTemplate {
        protected @Inject HubDimensionFactory hubDimensionFactory;
        private final @Getter ResourceLocation id;

        @Inject
        protected HubDimensionTemplate(Identifiers identifiers) {
            this.id = identifiers.get("hub");
            setRegistryName(id);
        }

        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return hubDimensionFactory::create;
        }
    }
}
