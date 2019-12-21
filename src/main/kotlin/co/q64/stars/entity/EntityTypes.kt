package co.q64.stars.entity

import co.q64.stars.id
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType

val pickupEntityType = EntityType.Builder.create({ _: EntityType<PickupEntity>, world -> PickupEntity(world) }, EntityClassification.MISC).apply {
    disableSerialization()
    size(0.5f, 0.5f)
    setCustomClientFactory { _, world -> PickupEntity(world) }
}.build("pickup").apply {
    id = "pickup"
}

val entityTypes by lazy {
    listOf(
            pickupEntityType
    )
}