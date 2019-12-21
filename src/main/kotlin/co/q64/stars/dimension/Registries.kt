package co.q64.stars.dimension

import co.q64.stars.dimension.fleeting.*
import co.q64.stars.dimension.fleeting.feature.DecayBlobFeature
import co.q64.stars.dimension.fleeting.feature.SolidDecayBlobFeature
import co.q64.stars.dimension.fleeting.placement.DecayBlobPlacement
import co.q64.stars.dimension.hub.HubBiome
import co.q64.stars.dimension.hub.HubDimensionTemplate

val biomes by lazy {
    listOf(
            FleetingBiome,
            FleetingSolidBiome,
            ChallengeBiome,
            HubBiome
    )
}

val dimensions by lazy {
    listOf(
            HubDimensionTemplate,
            FleetingDimensionTemplate,
            FleetingSolidDimensionTemplate,
            ChallengeDimensionTemplate
    )
}

val features by lazy {
    listOf(
            DecayBlobFeature,
            SolidDecayBlobFeature
    )
}

val placements by lazy {
    listOf(
            DecayBlobPlacement
    )
}