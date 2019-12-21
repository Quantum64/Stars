package co.q64.stars.listener

import co.q64.stars.capability.Gardener
import co.q64.stars.command.stars
import co.q64.stars.net.ChannelHolder
import co.q64.stars.util.NoopStorage
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerStartingEvent

object InitializationListener {

    @SubscribeEvent
    fun onInitialize(event: FMLCommonSetupEvent) {
        with(CapabilityManager.INSTANCE) {
            register(Gardener::class.java, NoopStorage<Gardener>()) { Gardener() }
        }
        ChannelHolder.register()
    }

    @SubscribeEvent
    fun onServerStars(event: FMLServerStartingEvent) = event.commandDispatcher.stars()
}