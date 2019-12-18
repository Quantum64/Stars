package co.q64.stars.dimension.fleeting

import co.q64.stars.id
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.provider.BiomeProviderType
import net.minecraft.world.biome.provider.SingleBiomeProvider
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder


sealed class StarsBiome : Biome(Builder()
        .surfaceBuilder(SurfaceBuilder.NOPE, SurfaceBuilder.AIR_CONFIG)
        .precipitation(RainType.NONE).category(Category.DESERT)
        .depth(0.1f).scale(0.2f)
        .temperature(0.5f)
        .downfall(0.0f)
        .waterColor(0xffffff)
        .waterFogColor(0xffffff)
        .parent(null as String?)) {
}

object FleetingBiome : StarsBiome() {
    init {
        id = "fleeting"
    }

    val provider: SingleBiomeProvider = BiomeProviderType.FIXED.create(SingleBiomeProviderSettings().setBiome(this))
}

object FleetingSolidBiome : StarsBiome() {
    init {
        id = "fleeting_solid"
    }

    val provider: SingleBiomeProvider = BiomeProviderType.FIXED.create(SingleBiomeProviderSettings().setBiome(this))
}

object ChallengeBiome : StarsBiome() {
    init {
        id = "challenge"
    }

    val provider: SingleBiomeProvider = BiomeProviderType.FIXED.create(SingleBiomeProviderSettings().setBiome(this))
}