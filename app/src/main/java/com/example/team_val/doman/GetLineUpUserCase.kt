package com.example.team_val.doman

import com.example.team_val.ResponseData
import com.example.team_val.data.network.firebase.FirestoreManager
import com.example.team_val.doman.model.LineUpModel
import com.example.team_val.doman.model.toLineUnModel
import javax.inject.Inject

class GetLineUpUserCase @Inject constructor(
    val firestoreManager: FirestoreManager
) {
    suspend operator fun invoke(mapName:String): ResponseData<List<LineUpModel>> {
        return firestoreManager.getLineUp(mapName)

    }
}