package co.q64.stars.dimension;

import co.q64.stars.util.Identifiers;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmptyBiome extends Biome {
    protected @Inject EmptyBiome(Identifiers identifiers) {
        super((new Biome.Builder())
                .surfaceBuilder(SurfaceBuilder.NOPE, SurfaceBuilder.AIR_CONFIG)
                .precipitation(Biome.RainType.NONE).category(Category.THEEND)
                .depth(0.1F).scale(0.2F)
                .temperature(0.5F)
                .downfall(0.5F)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .parent((String) null));
        setRegistryName(identifiers.get("empty"));
    }
}
