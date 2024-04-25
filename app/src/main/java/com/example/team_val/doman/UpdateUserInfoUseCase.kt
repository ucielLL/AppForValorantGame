package com.example.team_val.doman

import com.example.team_val.ResponseData
import com.example.team_val.data.network.RioApi
import com.example.team_val.data.network.RioService
import com.example.team_val.data.network.firebase.AuthManager
import com.example.team_val.data.network.firebase.FirestoreManager
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val firestore: FirestoreManager,
    private val rioApi: RioService
){
     suspend operator fun invoke(name: String, tag:String):ResponseData<Unit>{
        return when(val uservalornt = rioApi.searchUser(name,tag)){
             is ResponseData.Success->{
                 if (uservalornt.data !=null){
                     firestore.UpdateNameUser(uservalornt.data.gameName)
                 }else{ ResponseData.Error("Error, intente mas tarde")}
             }
             is ResponseData.Error->{
                 ResponseData.Error(uservalornt.messageError)
             }
         }
    }
}