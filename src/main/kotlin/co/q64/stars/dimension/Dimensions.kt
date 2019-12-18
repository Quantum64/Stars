package co.q64.stars.dimension

import co.q64.stars.dimension.fleeting.ChallengeDimensionTemplate
import co.q64.stars.dimension.fleeting.FleetingDimensionTemplate
import co.q64.stars.dimension.fleeting.FleetingSolidDimensionTemplate
import co.q64.stars.dimension.hub.HubDimensionTemplate

val dimensions by lazy {
    listOf(
            HubDimensionTemplate,
            FleetingDimensionTemplate,
            FleetingSolidDimensionTemplate,
            ChallengeDimensionTemplate
    )
}