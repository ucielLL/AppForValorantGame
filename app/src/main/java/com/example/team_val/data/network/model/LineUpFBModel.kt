package com.example.team_val.data.network.model

import com.example.team_val.doman.model.LineUpModel

data class LineUpFBModel(
    var  score: Int= 0,
    val duelists: String,
    val sentinel: String,
    val controller: String,
    val initiator: String,
    var player5: String,
    var timesQualified: Int = 0,
    var total: Int= 0
)
fun LineUpModel.toLineUpFB(): LineUpFBModel = LineUpFBModel(0,duelists,sentinel,controller,initiator,player5)