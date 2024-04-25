package com.example.team_val.doman

import com.example.team_val.doman.model.UserModel
import com.example.team_val.ResponseData
import com.example.team_val.data.network.firebase.FirestoreManager
import javax.inject.Inject


class CreateUserUseCase @Inject constructor (
    private val firestore: FirestoreManager,
) {

    suspend operator fun invoke (user: UserModel): ResponseData<Unit> {

        var respon = firestore.getUser(user.email)
        return  when (respon) {
            is ResponseData.Success -> {
                if (respon.data == null) {
                    firestore.CreateUser(user)
                } else {
                    ResponseData.Error("el correo ya esta en uso")
                }
            }
            is ResponseData.Error -> {
                ResponseData.Error(respon.messageError)
            }
                }

        }



}