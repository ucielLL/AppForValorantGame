package com.example.team_val.ui.view_model

import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team_val.ResponseData
import com.example.team_val.data.network.firebase.AuthManager
import com.example.team_val.data.network.firebase.AuthRes
import com.example.team_val.doman.GetUserUserCase
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
   private val getUserUseCase : GetUserUserCase,
   val authManager : AuthManager
)  :ViewModel() {

    private var _IsSigned= MutableLiveData<ResponseData<Unit>>()
    val isSigned : LiveData<ResponseData<Unit>> =_IsSigned
    private var _enableButoonligIn = MutableLiveData<Boolean>()
    var email: String = ""
     fun signInGoogle(activityResult: ActivityResult){
         viewModelScope.launch {
             when (val account = authManager.handdleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(activityResult.data))){
                 is AuthRes.Success ->{
                     when(val isUserExist= account.data.email?.let { getUserUseCase.invoke(it)}){
                         is ResponseData.Success->{
                             val credential = GoogleAuthProvider.getCredential(account?.data?.idToken,null)
                                 val fireUser = authManager.signInGoogleCredential(credential)
                                 if (fireUser!= null){
                                    _IsSigned.postValue(ResponseData.Success(Unit))
                                 }
                                 else{_IsSigned.postValue(ResponseData.Error(" error al iniciar sesión"))}
                             }
                         is ResponseData.Error->{
                             _IsSigned.postValue(ResponseData.Error(isUserExist.messageError ))
                         }
                         else->{
                             _IsSigned.postValue(ResponseData.Error("No se encontro usuario"))
                         }
                     }                }
                 is AuthRes.Error->{
                     _IsSigned.postValue(ResponseData.Error(account.messageError ))
                 }
                 else->{
                     _IsSigned.postValue(ResponseData.Error("no fue posible iniciar sesión"))
                 }

             }

         }
        }

    fun signInWithEmail(password: String){
        viewModelScope.launch {
            when(val searchUser = getUserUseCase.invoke(email)){
                is ResponseData.Success ->{
                   when(val user= authManager.signInUserWithEmailAndPassoword(email,password)){
                       is AuthRes.Success->{
                           if(user.data != null){
                               _IsSigned.postValue(ResponseData.Success(Unit))
                           }
                           else{_IsSigned.postValue(ResponseData.Error(" error al iniciar sesión"))}
                       }
                       is AuthRes.Error->{
                           _IsSigned.postValue(ResponseData.Error(user.messageError ))
                       }
                       else -> {}
                   }
                }
                is ResponseData.Error->{
                    _IsSigned.postValue(ResponseData.Error("No se encontro usuario"))
                }
            }
        }

    }

}