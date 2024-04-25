package com.example.team_val.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_val.ResponseData
import com.example.team_val.data.network.ValorantService
import com.example.team_val.data.network.model.AgentsModel
import com.example.team_val.doman.model.MapModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AgentsViewModel @Inject constructor(
    private val valorantService : ValorantService
):ViewModel()
{
    private var _listAgent = MutableLiveData<List<AgentsModel>>()
    var listagents: MutableLiveData<List<AgentsModel>> = _listAgent
    private var _responseMessage = MutableLiveData<String>()
    val responseMessage : LiveData<String> = _responseMessage
    var positionDetile : Int =0
    fun getAgent(){
      viewModelScope.launch(Dispatchers.IO) {
        when(val data= valorantService.getAgents()){
            is ResponseData.Success -> {
                withContext(Dispatchers.Main) {
                   _listAgent.postValue(data.data?: emptyList())
                }
            }
                is ResponseData.Error-> {
                    withContext(Dispatchers.Main) {
                        _responseMessage.postValue(data.messageError)
                    }
                }
            }
        }
      }
    fun getAgentDetail():ResponseData<AgentsModel>{
        return if(_listAgent.value != null )
        {ResponseData.Success(_listAgent.value!![positionDetile])}
        else{
            ResponseData.Error("Error al cargar datos")
        }
    }

}

