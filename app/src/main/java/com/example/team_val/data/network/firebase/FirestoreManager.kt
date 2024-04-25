package com.example.team_val.data.network.firebase

import android.content.ContentValues
import android.util.Log
import com.bumptech.glide.annotation.GlideModule
import com.example.team_val.ResponseData
import com.example.team_val.data.network.model.ConstsModel
import com.example.team_val.data.network.model.LineUpFBModel
import com.example.team_val.data.network.model.toUserFirebase
import com.example.team_val.doman.model.LineUpModel
import com.example.team_val.doman.model.UserModel
import com.example.team_val.doman.model.toLineUnModel
import com.example.team_val.ui.adapters.LineUpAdapter
import com.google.android.gms.common.api.internal.TaskApiCall
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.logging.Handler
import javax.inject.Inject
import kotlin.concurrent.schedule
import kotlin.coroutines.coroutineContext

class FirestoreManager @Inject constructor(
   private val fireBaseClient: FireBaseClient
) {

    suspend fun CreateUser(user: UserModel): ResponseData<Unit> {
        var isSuccessful = false
        var messageFailed = ""
        var respon =
            fireBaseClient.db.collection("Users").document(user.email).set(user.toUserFirebase())
                .addOnSuccessListener {
                    isSuccessful = true
                }.addOnFailureListener {
                isSuccessful = false
                messageFailed = it.message ?: "Error al crear usuario"
            }.await()
        return if (isSuccessful) ResponseData.Success(Unit) else ResponseData.Error(messageFailed)
    }
    suspend fun getUser(email: String): ResponseData<UserModel?> {

        val docRef = fireBaseClient.db.collection("Users").document(email)
        var user: UserModel? = null
        var messageFailed = ""
        var isSuccessful = false

        docRef.get().addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject<UserModel>()
            isSuccessful = true
        }
            .addOnFailureListener {
                messageFailed = it.message ?: "Error al buscar similitudes de usuario"
                isSuccessful = false
            }.await()
        return if (isSuccessful) ResponseData.Success(user) else ResponseData.Error(messageFailed)
    }
    suspend fun delateUser(email: String): ResponseData<Unit> {
        var isSuccessful = false
        var messageFailed = ""

        fireBaseClient.db.collection("Users").document(email).delete()
            .addOnSuccessListener {
                isSuccessful = true

            }
            .addOnFailureListener {
                messageFailed = it.message ?: "Error al eliminar usuario"
            }.await()

        return if (isSuccessful) ResponseData.Success(Unit) else ResponseData.Error(messageFailed)
    }

    fun UpdateNameUser(nameUser: String): ResponseData<Unit> {
        var isSuccessful = false
        var messageFailed = " "
        fireBaseClient.db.collection("Users")
            .document(fireBaseClient.auth.currentUser?.email.toString())
            .update("name", nameUser)
            .addOnSuccessListener {
                isSuccessful = true
            }
            .addOnFailureListener {
                isSuccessful = false
                messageFailed = it.message ?: "Error al actualizar datos, intente mas tarde"
            }
        return if (isSuccessful) ResponseData.Success(Unit) else ResponseData.Error(messageFailed)
    }


    suspend fun crearLineUp(map: String, name: String, lineup: LineUpFBModel): ResponseData<Unit> {
        var isSuccessful = false
        var messageFailed = ""

        var response = fireBaseClient.db.collection(map).document(name).set(lineup)
            .addOnSuccessListener {
                isSuccessful = true
            }
            .addOnFailureListener {
                messageFailed = if (it.message?.isBlank()== true) "Error al crear alineacion" else it.message!!
            }.await()

        return if (isSuccessful) ResponseData.Success(Unit) else ResponseData.Error(messageFailed)
    }
    suspend fun getLineUp(nameMap:String):ResponseData<List<LineUpModel>>{
        var isSuccessful = false
        var messageFailed = ""
        var list : List<LineUpModel> = listOf()

        Log.w(ContentValues.TAG, "SE EJECUTA ")
        var d = fireBaseClient.db.collection(nameMap).get()
        if (d.isSuccessful){
            var it = d.result
            list = it.documents.map { (it.toObject<LineUpFBModel>())!!.toLineUnModel(it.id) }.toList()
            isSuccessful = true
        }
        else{
            messageFailed =  d.exception?.message.toString()
        }
      /* fireBaseClient.db.collection(nameMap).get()
           .addOnSuccessListener {
                //Timer().schedule(10000){
                    if(it.documents.isNotEmpty()){
                        list = it.documents.map { (it.toObject<LineUpFBModel>())!!.toLineUnModel(it.id) }.toList()
                        isSuccessful = true
                    }
            }
            .addOnFailureListener {
               // messageFailed = if (it.message?.isEmpty()== true) "Error: no hay datos" else it.message!!
              messageFailed = "falla"
                Log.w(ContentValues.TAG, "AGREGA FALLA  ")
            }.
              addOnCompleteListener {  if (it.result.isEmpty){ }
                   Log.w(ContentValues.TAG, "TERMINA TASK ") }.await()
*/
        Log.w(ContentValues.TAG, "TERMINA ")
        return if (isSuccessful) ResponseData.Success(list) else ResponseData.Error(messageFailed)
    }

    suspend fun getConsts(): ResponseData<ConstsModel?> {
        val docRef = fireBaseClient.db.collection("general").document("consts")
        var data: ConstsModel? = null
        var messageFailed = ""
        var isSuccessful = false
        var v: ResponseData<ConstsModel?> = ResponseData.Error(messageFailed)

        var e = docRef.get().addOnFailureListener {
            messageFailed = it.message ?: "Error intente mas tarde"
        }.addOnSuccessListener {
            var d = it.data?.get("maps")
            if (d != null) {
                isSuccessful = true
                data = ConstsModel((d as HashMap<String, String>))
                Log.w(ContentValues.TAG, "YA PEDIR LA INFO")
            }
        }.await()
        docRef.get().
        addOnCompleteListener {
            v = if (isSuccessful) ResponseData.Success(data) else ResponseData.Error(messageFailed)
            Log.w(ContentValues.TAG, "CO,MPLETADO")
        }.await()


        Log.w(ContentValues.TAG, "CONTUNUE TAREA ")
        data?.maps?.forEach { Log.w(ContentValues.TAG, "PATH" + it.value) }
        return v
    }
}

