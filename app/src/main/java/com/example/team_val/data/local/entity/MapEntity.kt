package com.example.team_val.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "map_table")
data class MapEntity(
    @PrimaryKey
    val name : String,
    val path: String
)