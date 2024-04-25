package com.example.team_val.doman.model

data class UserModel(
    var name : String="",
    var email: String="",
    var  isUserPro: Boolean= false,
    var  teamById: List<String>? = emptyList(),
    var lineUpScore: HashMap<String,Int>? = hashMapOf()
)
