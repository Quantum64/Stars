package co.q64.stars.dimension.fleeting;

import co.q64.stars.dimension.fleeting.feature.DecayBlobFeature;
import co.q64.stars.dimension.fleeting.placement.DecayBlobPlacement;
import co.q64.stars.util.Identifiers;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FleetingBiome extends Biome {
    protected @Inject DecayBlobFeature decayBlobFeature;
    protected @Inject DecayBlobPlacement decayBlobPlacement;

    protected @Inject FleetingBiome(Identifiers identifiers) {
        super((new Biome.Builder())
                .surfaceBuilder(SurfaceBuilder.NOPE, SurfaceBuilder.AIR_CONFIG)
                .precipitation(Biome.RainType.NONE).category(Category.DESERT)
                .depth(0.1F).scale(0.2F)
                .temperature(0.5F)
                .downfall(0.5F)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .parent((String) null));
        setRegistryName(identifiers.get("fleeting"));
    }

    @Inject
    protected void setup() {
        addFeature(Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(decayBlobFeature, IFeatureConfig.NO_FEATURE_CONFIG, decayBlobPlacement, IPlacementConfig.NO_PLACEMENT_CONFIG));
    }
}
