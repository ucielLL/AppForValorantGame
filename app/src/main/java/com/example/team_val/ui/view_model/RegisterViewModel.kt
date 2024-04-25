package com.example.team_val.ui.view_model

import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_val.doman.CreateUserUseCase
import com.example.team_val.doman.model.UserModel
import com.example.team_val.ResponseData
import com.example.team_val.data.network.RioService
import com.example.team_val.data.network.firebase.AuthManager
import com.example.team_val.data.network.firebase.AuthRes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
   private  val apiRioService: RioService,
    private val createUserUseCase: CreateUserUseCase,
   val authManager : AuthManager
) :ViewModel() {
    private val _buttonRegiterEnable = MutableLiveData<Boolean>()
    val butoonRegiterEnable: LiveData<Boolean> = _buttonRegiterEnable
    private val _butoonGoogleEnable = MutableLiveData<Boolean>()
    val buttonGoogleEnable: LiveData<Boolean> = _butoonGoogleEnable
    private var _isCreateUser = MutableLiveData<ResponseData<Unit>>()

    var isCreateUser: LiveData<ResponseData<Unit>> = _isCreateUser

    var userName: String = ""
    var tagline: String = ""
    var email: String = ""
    var isPasswordcheked: Boolean = false


    fun authManagerInit() {
        _butoonGoogleEnable.postValue(false)
        _buttonRegiterEnable.postValue(false)
    }

    fun enableButtonGoogle() {
        if (!userName.isNullOrEmpty() && !tagline.isNullOrEmpty()) {
            _butoonGoogleEnable.postValue(true)
        } else {
            _butoonGoogleEnable.postValue(false)
        }
    }
    fun enableButtonRegiter() {
        if (!userName.isNullOrEmpty() && !tagline.isNullOrEmpty() && !email.isNullOrEmpty() && isPasswordcheked) {
            _buttonRegiterEnable.postValue(true)
        } else {
            _buttonRegiterEnable.postValue(false)
        }
    }
    fun createUserWithEmailAndPassword(password: String) {
        val user = UserModel(userName, email, false)
        viewModelScope.launch {
            val respon :  ResponseData<Unit>  = withContext(Dispatchers.IO){
               return@withContext when (val userValorant = apiRioService.searchUser(userName, tagline)) {
                    is ResponseData.Success -> {
                        when (val auth = authManager.creatUserWithEmailAndPassoword(email, password)) {
                            is AuthRes.Success -> {
                              createUserUseCase.invoke(user)
                            }

                            is AuthRes.Error -> {
                              ResponseData.Error(auth.messageError)
                            }
                        }
                    }
                    is ResponseData.Error -> {
                     ResponseData.Error(userValorant.messageError)
                    }
                }
            }
            _isCreateUser.postValue(respon)
        }
    }
    fun createUserWithGoogle(activityResult: ActivityResult) {
        viewModelScope.launch {
             val respon :  ResponseData<Unit>  = withContext(Dispatchers.IO){

                 return@withContext when (val userValorant = apiRioService.searchUser(userName, tagline)) {
                     is ResponseData.Success -> {
                         when (val account = authManager.handdleSignInResult(
                             GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                         )) {
                             is AuthRes.Success -> {
                                 val credential =
                                     GoogleAuthProvider.getCredential(account?.data?.idToken, null)

                                 when(  val fireUser = authManager.signInGoogleCredential(credential)){
                                     is AuthRes.Success->{
                                         val user = UserModel(userName, fireUser.data.email!!, false)
                                        createUserUseCase.invoke(user)
                                     }
                                     is AuthRes.Error->{  ResponseData.Error(fireUser.messageError) }
                                 }
                             }
                             is AuthRes.Error -> {
                                 ResponseData.Error(account.messageError)
                             }
                             else->{
                                 ResponseData.Error("Error desconocido")
                             }
                         }
                     }
                     is ResponseData.Error -> {
                         ResponseData.Error(userValorant.messageError)
                     }
                 }
             }
            _isCreateUser.postValue(respon)
        }
    }
}

