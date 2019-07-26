package co.q64.stars.dimension;

import co.q64.stars.util.Identifiers;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.BiFunction;

@Singleton
public class Dimensions {
    protected @Inject Identifiers identifiers;
    protected @Inject co.q64.stars.dimension.fleeting.FleetingDimensionFactory fleetingDimensionFactory;
    protected @Inject co.q64.stars.dimension.hub.HubDimensionFactory hubDimensionFactory;

    private @Getter DimensionType fleetingDimensionType;
    private @Getter DimensionType hubDimensionType;

    protected @Inject Dimensions() {}

    public void register() {
        this.fleetingDimensionType = register(identifiers.get("fleeting"), fleetingDimensionFactory::create);
        this.hubDimensionType = register(identifiers.get("hub"), hubDimensionFactory::create);
    }

    private DimensionType register(ResourceLocation id, BiFunction<World, DimensionType, ? extends Dimension> factory) {
        return DimensionManager.registerDimension(id, new ModDimension() {
            public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
                return factory;
            }
        }.setRegistryName(id), null, false);
    }
}
