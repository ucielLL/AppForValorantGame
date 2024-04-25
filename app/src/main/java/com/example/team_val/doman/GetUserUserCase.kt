package com.example.team_val.doman

import com.example.team_val.ResponseData
import com.example.team_val.data.network.firebase.FirestoreManager
import javax.inject.Inject

class GetUserUserCase @Inject constructor (
    private val firestore: FirestoreManager,

    )  {
    suspend operator fun invoke(email: String):ResponseData<Unit>{
       return when(val user= firestore.getUser(email)){
           is ResponseData.Success->{
               if(user.data !=null){
                   ResponseData.Success(Unit)
               }else{ ResponseData.Error("Usuario No encontrado")}
           }
           is ResponseData.Error->{ResponseData.Error(user.messageError)}
       }
    }
}