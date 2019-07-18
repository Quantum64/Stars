package co.q64.stars.dimension;

import co.q64.stars.binders.ConstantBinders.ModId;
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
    protected @Inject @ModId String modId;
    protected @Inject AdventureDimensionFactory dimensionFactory;

    private @Getter DimensionType adventureDimensionType;

    protected @Inject Dimensions() {}

    public void register() {
        ResourceLocation dimensionId = new ResourceLocation(modId, "stars");
        this.adventureDimensionType = DimensionManager.registerDimension(dimensionId, new ModDimension() {
            public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
                return dimensionFactory::create;
            }
        }.setRegistryName(dimensionId), null, false);
    }
}