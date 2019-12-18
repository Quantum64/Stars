package co.q64.stars.dimension.hub

import co.q64.stars.id
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.provider.BiomeProviderType
import net.minecraft.world.biome.provider.SingleBiomeProvider
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder

object HubBiome : Biome(Builder()
        .surfaceBuilder(SurfaceBuilder.NOPE, SurfaceBuilder.AIR_CONFIG)
        .precipitation(RainType.NONE).category(Category.DESERT)
        .depth(0.1f).scale(0.2f)
        .temperature(0.5f)
        .downfall(0.0f)
        .waterColor(0xffffff)
        .waterFogColor(0xffffff)
        .parent(null as String?)) {
    init {
        id = "hub"
    }

    val provider: SingleBiomeProvider = BiomeProviderType.FIXED.create(SingleBiomeProviderSettings().setBiome(this))
}