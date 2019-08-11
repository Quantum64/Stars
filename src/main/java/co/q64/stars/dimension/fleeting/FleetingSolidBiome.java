package co.q64.stars.dimension.fleeting;

import co.q64.stars.dimension.fleeting.feature.SolidDecayBlobFeature;
import co.q64.stars.dimension.fleeting.placement.DecayBlobPlacement;
import co.q64.stars.util.Identifiers;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FleetingSolidBiome extends FleetingBiome {
    protected @Inject SolidDecayBlobFeature decayBlobFeature;
    protected @Inject DecayBlobPlacement decayBlobPlacement;

    protected @Inject FleetingSolidBiome(Identifiers identifiers) {
        setRegistryName(identifiers.get("fleeting_solid"));
    }

    @Inject
    protected void setup() {
        addFeature(Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(decayBlobFeature, IFeatureConfig.NO_FEATURE_CONFIG, decayBlobPlacement, IPlacementConfig.NO_PLACEMENT_CONFIG));
    }
}
