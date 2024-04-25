package com.example.team_val.data.network

import com.example.team_val.data.network.model.RootResponse
import retrofit2.Response

import retrofit2.http.GET

interface ValorantApi {
    @GET("/v1/agents?language=es-MX")
    suspend fun getAgents(): Response<RootResponse>
}