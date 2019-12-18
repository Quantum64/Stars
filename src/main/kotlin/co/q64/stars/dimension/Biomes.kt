package co.q64.stars.dimension

import co.q64.stars.dimension.fleeting.ChallengeBiome
import co.q64.stars.dimension.fleeting.FleetingBiome
import co.q64.stars.dimension.fleeting.FleetingSolidBiome
import co.q64.stars.dimension.hub.HubBiome

val biomes by lazy {
    listOf(
            FleetingBiome,
            FleetingSolidBiome,
            ChallengeBiome,
            HubBiome
    )
}