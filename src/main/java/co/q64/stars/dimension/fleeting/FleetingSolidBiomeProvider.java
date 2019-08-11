package co.q64.stars.dimension.fleeting;

import co.q64.stars.util.Identifiers;
import lombok.Getter;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FleetingSolidBiomeProvider {
    protected @Getter BiomeProvider provider;

    protected @Inject FleetingSolidBiomeProvider(Identifiers identifiers, FleetingSolidBiome biome) {
        provider = BiomeProviderType.FIXED.create(new SingleBiomeProviderSettings().setBiome(biome));
    }
}
