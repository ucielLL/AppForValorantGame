package com.example.team_val.data.network

import com.example.team_val.data.network.model.UserValorantModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RioApi {
    @GET("/riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}")
  suspend fun searchUserValorant(@Path("gameName") gameName:String, @Path("tagLine") tagLine : String):Response<UserValorantModel>

}


