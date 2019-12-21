package co.q64.stars.capability

import kotlinx.serialization.Serializable

@Serializable
data class Hub(var next: Int = 0)