package com.example.team_val.ui.view_model

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_val.ResponseData
import com.example.team_val.data.network.ValorantService
import com.example.team_val.data.network.model.AgentsModel
import com.example.team_val.doman.DownloadDataUseCase
import com.example.team_val.doman.GetMapaUseCase
import com.example.team_val.doman.model.LineUpModel
import com.example.team_val.doman.model.MapModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainVieModel @Inject constructor(


) : ViewModel()  {

    private var _responseMessage = MutableLiveData<String>()
    val responseMessage : LiveData<String> = _responseMessage

}