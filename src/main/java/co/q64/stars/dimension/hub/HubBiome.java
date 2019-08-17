package co.q64.stars.dimension.hub;

import co.q64.stars.util.Identifiers;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HubBiome extends Biome {

    protected @Inject HubBiome(Identifiers identifiers) {
        super((new Builder())
                .surfaceBuilder(SurfaceBuilder.NOPE, SurfaceBuilder.AIR_CONFIG)
                .precipitation(RainType.NONE).category(Category.DESERT)
                .depth(0.1F).scale(0.2F)
                .temperature(0.5F)
                .downfall(0.0F)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .parent((String) null));
        setRegistryName(identifiers.get("hub"));
    }

    @Inject
    protected void setup() {
    }
}
