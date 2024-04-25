package com.example.team_val.data.network.model

import com.example.team_val.doman.model.UserModel

data class UserModelFireBase(
    val name: String,
    val isUserPro: Boolean,
    val teamById: List<String>?= emptyList() ,
    val lineUpScore: HashMap<String,Int>?
)
fun UserModel.toUserFirebase() = UserModelFireBase(name, isUserPro, teamById, lineUpScore)
