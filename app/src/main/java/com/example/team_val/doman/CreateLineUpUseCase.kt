package com.example.team_val.doman

import com.example.team_val.data.network.firebase.FirestoreManager
import com.example.team_val.data.network.model.toLineUpFB
import com.example.team_val.doman.model.LineUpModel
import javax.inject.Inject

class CreateLineUpUseCase @Inject constructor(
    val firestoreManager: FirestoreManager
) {

   suspend operator fun invoke(map:String,lineUp: LineUpModel){
       firestoreManager.crearLineUp(map,lineUp.name,lineUp.toLineUpFB())
    }
}
