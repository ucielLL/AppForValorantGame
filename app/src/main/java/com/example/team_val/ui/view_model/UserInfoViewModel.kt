package com.example.team_val.ui.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_val.ResponseData
import com.example.team_val.data.network.firebase.FireBaseClient
import com.example.team_val.doman.DelateUserUseCase
import com.example.team_val.doman.UpdateUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel  @Inject constructor(
    private val auth: FireBaseClient,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val delateUserUseCase: DelateUserUseCase

): ViewModel() {

    var email: String =""
    val name: String=""
    private var _responseMessage = MutableLiveData<String>()
    val responseMessage : LiveData<String> = _responseMessage
    private var _responsDelateUser = MutableLiveData<ResponseData<Unit>>()
    val responsDelateUser  : LiveData<ResponseData<Unit>> = _responsDelateUser

    fun loadData():Pair<String,String> {
         email = auth.auth.currentUser?.email.toString()
        return Pair(email?:"not found","uciel")
    }
    fun singOut(){
        auth.auth.signOut()
    }
    fun updateInfo(name: String, tag:String) {
        if(!name.isNullOrEmpty()&& !tag.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                when (val reponse = updateUserInfoUseCase.invoke(name, tag)) {
                    is ResponseData.Success -> {
                        withContext(Dispatchers.Main) { _responseMessage.postValue("Información actualizada") }
                    }

                    is ResponseData.Error -> {
                        withContext(Dispatchers.Main) { _responseMessage.postValue(reponse.messageError) }
                    }
                }

            }
        }
        else{
            _responseMessage.postValue("no puede haber campos vacios")
        }
    }
    fun delateUser (){
        if(!email.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val respons = delateUserUseCase.invoke(email)
                withContext(Dispatchers.Main) { _responsDelateUser.postValue(respons) }

            }
        }else{
            _responseMessage.postValue("Error, intente mas tarde")
        }
    }
}
