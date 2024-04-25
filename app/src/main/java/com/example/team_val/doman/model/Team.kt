package com.example.team_val.doman.model

data class Team(
    val name: String,
    val gamerStatus: HashMap<String,Int>, //user/status
    val  lineupList : List<LineUp>
)
data class LineUp(
    val nameMap: String,
    val map : HashMap<String,String> // agent/ user
)

