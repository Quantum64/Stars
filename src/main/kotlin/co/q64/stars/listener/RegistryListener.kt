package co.q64.stars.listener

import co.q64.stars.block.blocks
import co.q64.stars.tile.tileTypes
import net.minecraft.block.Block
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.eventbus.api.SubscribeEvent

object RegistryListener {
    @SubscribeEvent
    fun onBlockRegistry(event: Register<Block>) = blocks.forEach { event.registry.register(it) }

    @SubscribeEvent
    fun onTileTypeRegistry(event: Register<TileEntityType<*>>) = tileTypes.forEach { event.registry.register(it) }
}