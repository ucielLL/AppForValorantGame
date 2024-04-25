package com.example.team_val.data.network.model

data class AgentsModel(
    val displayName: String,
    val description: String,
    val displayIcon: String,
    val fullPortrait: String,
    val backgroundGradientColors: List<String>,
    val role: Role?,
    val abilities: List<Ability>,

)

data class Role(
    val displayName: String,
    val description: String,
    val displayIcon: String,
)

data class Ability(
    val slot: String,
    val displayName: String,
    val description: String,
    val displayIcon: String?,
)

data class RootResponse(
    val status: Long,
    val data: List<AgentsModel>
)
