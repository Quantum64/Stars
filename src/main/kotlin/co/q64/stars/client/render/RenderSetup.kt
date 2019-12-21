package co.q64.stars.client.render

import co.q64.stars.client.render.tile.tesrs
import net.minecraftforge.fml.client.registry.ClientRegistry

fun initializeRendering() {
    tesrs.forEach { (type, render) ->
        ClientRegistry.bindTileEntitySpecialRenderer(type.java, render)
    }
}