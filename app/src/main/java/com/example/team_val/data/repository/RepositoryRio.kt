package com.example.team_val.data.repository

import com.example.team_val.ResponseData
import com.example.team_val.data.network.RioService
import com.example.team_val.data.network.model.UserValorantModel
import javax.inject.Inject

class RepositoryRio @Inject constructor(
    private val api: RioService
) {
    suspend fun searchUserValorant(gamerName:String, tagLine : String): ResponseData<UserValorantModel?>{
        return api.searchUser(gamerName,tagLine)
    }
}