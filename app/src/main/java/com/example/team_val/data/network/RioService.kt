package com.example.team_val.data.network

import com.example.team_val.ResponseData
import com.example.team_val.data.network.model.UserValorantModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject
import kotlin.contracts.Returns


class RioService @Inject constructor(private val api: RioApi) {

suspend fun searchUser(gameName:String, tagLine : String):ResponseData<UserValorantModel?>{
    return withContext(Dispatchers.IO){
        try {
            val response = api.searchUserValorant(gameName,tagLine)
            if(response.body() != null ){


                ResponseData.Success(response.body())
            }else{
                ResponseData.Error("Usuario no encontrado en red de valorant")
            }


        }
        catch (e:Exception){
            return@withContext ResponseData.Error("Usuario no encontrado en red de valorant:${e.message}")
        }


    }

}

}