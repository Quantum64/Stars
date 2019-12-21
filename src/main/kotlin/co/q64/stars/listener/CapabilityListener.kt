package co.q64.stars.listener

import co.q64.stars.capability.Gardener
import co.q64.stars.id
import co.q64.stars.util.CapabilityBase
import co.q64.stars.util.gardenerCapability
import net.minecraft.entity.Entity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object CapabilityListener {
    @SubscribeEvent
    fun onCapabilityAttachEntity(event: AttachCapabilitiesEvent<Entity>) {
        if (event.getObject() is ServerPlayerEntity) {
            event.addCapability("gardener".id, CapabilityBase(gardenerCapability, Gardener.serializer()))
        }
    }
}