package com.example.team_val.doman

import android.content.Context
import com.example.team_val.ResponseData
import com.example.team_val.data.network.firebase.AuthManager
import com.example.team_val.data.network.firebase.FirestoreManager
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class DelateUserUseCase @Inject constructor(
    private  val auth: AuthManager,
    private val firestore: FirestoreManager
) {
    suspend operator fun invoke(email:String):ResponseData<Unit>{
       // return   auth.DelateUser()

       return when(val dbUserDelate = firestore.delateUser(email)){
            is ResponseData.Success->{
                auth.DelateUser()
            }
            is ResponseData.Error -> {
               ResponseData.Error(dbUserDelate.messageError)
            }
        }


    }

}