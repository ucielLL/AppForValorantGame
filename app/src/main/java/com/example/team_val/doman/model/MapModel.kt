package com.example.team_val.doman.model

import android.graphics.Bitmap
import com.example.team_val.data.local.entity.MapEntity
import com.example.team_val.data.network.model.ConstsModel

data class MapModel(
    val name: String,
    val path:String
)
fun MapEntity.toMapModel() = MapModel(name, path)
