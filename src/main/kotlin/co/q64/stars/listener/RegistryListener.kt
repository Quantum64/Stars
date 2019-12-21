package co.q64.stars.listener

import co.q64.stars.block.blocks
import co.q64.stars.dimension.biomes
import co.q64.stars.dimension.dimensions
import co.q64.stars.dimension.features
import co.q64.stars.dimension.placements
import co.q64.stars.entity.entityTypes
import co.q64.stars.id
import co.q64.stars.item.items
import co.q64.stars.tile.tileTypes
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraft.world.biome.Biome
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.placement.Placement
import net.minecraftforge.common.DimensionManager
import net.minecraftforge.common.ModDimension
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.event.world.RegisterDimensionsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object RegistryListener {
    @SubscribeEvent
    fun onBlockRegistry(event: Register<Block>) = blocks.forEach { event.registry.register(it) }

    @SubscribeEvent
    fun onItemRegistry(event: Register<Item>) = items.forEach { event.registry.register(it) }

    @SubscribeEvent
    fun onTileTypeRegistry(event: Register<TileEntityType<*>>) = tileTypes.forEach { event.registry.register(it) }

    @SubscribeEvent
    fun onBiomeRegistry(event: Register<Biome>) = biomes.forEach { event.registry.register(it) }

    @SubscribeEvent
    fun onDimensionRegistry(event: Register<ModDimension>) = dimensions.forEach { event.registry.register(it) }

    @SubscribeEvent
    fun onFeatureRegistry(event: Register<Feature<*>>) = features.forEach { event.registry.register(it) }

    @SubscribeEvent
    fun onPlacementRegistry(event: Register<Placement<*>>) = placements.forEach { event.registry.register(it) }

    @SubscribeEvent
    fun onEntityTypeRegistry(event: Register<EntityType<*>>) = entityTypes.forEach { event.registry.register(it) }

    @SubscribeEvent
    fun onRegisterDimensions(event: RegisterDimensionsEvent) = dimensions.forEach {
        if (DimensionType.byName(it.registryName ?: "invalid".id) == null) {
            DimensionManager.registerDimension(it.registryName, it, null, false)
        }
    }
}