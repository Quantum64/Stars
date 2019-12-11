package co.q64.stars

import co.q64.stars.listener.listeners
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

fun start() {
    listeners.forEach {
        MinecraftForge.EVENT_BUS.register(it)
        FMLJavaModLoadingContext.get().modEventBus.register(it)
    }
}