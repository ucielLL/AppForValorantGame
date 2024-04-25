package com.example.team_val.data.network

import com.example.team_val.ResponseData
import com.example.team_val.data.network.model.AgentsModel
import javax.inject.Inject

class ValorantService @Inject constructor(
    private val api : ValorantApi
) {

    suspend fun getAgents():ResponseData<List<AgentsModel>>{
      return  try{
            val list =api.getAgents()
          if(list.body()!= null ){
              ResponseData.Success(list.body()!!.data)
          }
          else{
              ResponseData.Error("Error: no obtuvieron datos correctamente ")
          }
      }
        catch (e:Exception){
            ResponseData.Error("Error al cargar datos:${e.message}")
        }
    }






}