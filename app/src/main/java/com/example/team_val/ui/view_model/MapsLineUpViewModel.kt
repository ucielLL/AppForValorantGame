package com.example.team_val.ui.view_model

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_val.ResponseData
import com.example.team_val.doman.DownloadDataUseCase
import com.example.team_val.doman.GetLineUpUserCase
import com.example.team_val.doman.GetMapaUseCase
import com.example.team_val.doman.model.LineUpModel
import com.example.team_val.doman.model.MapModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapsLineUpViewModel @Inject constructor(
    val getMapa: GetMapaUseCase,
    val downloadData: DownloadDataUseCase,
    val getLineUps: GetLineUpUserCase
) : ViewModel() {
    private var _listMap = MutableLiveData<List<MapModel>>()
    var listMap: LiveData<List<MapModel>> = _listMap
    private var _responseMessage = MutableLiveData<String>()
    val responseMessage : LiveData<String> = _responseMessage


    //lineuplist
    private var _listLineUp = MutableLiveData<List<LineUpModel>>()
    var listLineUp: LiveData<List<LineUpModel>> = _listLineUp
    var positionLineUp : Int =0

    fun downloadata(context: Context){
        viewModelScope.launch (Dispatchers.IO) {
            when(val data = downloadData.invoke(context)){
                is ResponseData.Success->{
                }
                is ResponseData.Error -> {
                    withContext(Dispatchers.Main){
                        _responseMessage.postValue(data.messageError)
                    }
                }
            }
        }
    }
    fun loadMaps(){
        viewModelScope.launch(Dispatchers.IO) {
            val  maps= getMapa.invoke()
            maps.forEach{   Log.w(ContentValues.TAG,  it.path)}
            withContext(Dispatchers.Main){
                _listMap.postValue(maps)
                Log.w(ContentValues.TAG, "SE CARGAN DATOS LISTA ")
            }
        }
    }
    //lineunlist

    fun getLineUps(nameMap:String){
        viewModelScope.launch(Dispatchers.IO){
            when(val data = getLineUps.invoke(nameMap)){
                is ResponseData.Success ->{
                    withContext(Dispatchers.Main){
                        _listLineUp.postValue(data.data)
                    }
                }
                is ResponseData.Error ->{
                    withContext(Dispatchers.Main){
                        _responseMessage.postValue(data.messageError)
                    }
                }
            }
        }
    }

}