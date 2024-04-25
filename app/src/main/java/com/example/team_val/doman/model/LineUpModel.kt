package com.example.team_val.doman.model

import com.example.team_val.data.network.model.LineUpFBModel

class LineUpModel(
    val name : String,
    var  score: Int= 0,
    val duelists: String,
    val sentinel: String,
    val controller: String,
    val initiator: String,
    val player5: String
)

fun LineUpFBModel.toLineUnModel(name: String): LineUpModel = LineUpModel(name,score,duelists,sentinel,controller,initiator,player5)

