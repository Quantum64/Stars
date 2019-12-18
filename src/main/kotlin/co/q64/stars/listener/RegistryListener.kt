package co.q64.stars.listener

import co.q64.stars.block.blocks
import co.q64.stars.dimension.biomes
import co.q64.stars.dimension.dimensions
import co.q64.stars.item.items
import co.q64.stars.tile.tileTypes
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraft.world.biome.Biome
import net.minecraftforge.common.ModDimension
import net.minecraftforge.event.RegistryEvent.Register
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
}