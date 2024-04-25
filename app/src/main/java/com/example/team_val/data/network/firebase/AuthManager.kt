package com.example.team_val.data.network.firebase

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract.CommonDataKinds.Identity
import androidx.activity.result.ActivityResultLauncher
import com.example.team_val.R
import com.example.team_val.ResponseData

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthManager @Inject  constructor(
    private val  fireBaseClient: FireBaseClient
) {


    fun signOut(){

        fireBaseClient.auth.signOut()
    }
    fun getCurrentUser():FirebaseUser?{

        return  fireBaseClient.auth.currentUser
    }
    suspend fun DelateUser():ResponseData<Unit>{

        var isSuccessful = false
        var messageFailed = ""

        fireBaseClient.auth.currentUser?.delete()?.addOnCompleteListener{  isSuccessful = true  }
            ?.addOnFailureListener {
                messageFailed ="Error al eliminar usuario:${it.message} "
            }?.await()
        return if(isSuccessful) ResponseData.Success(Unit) else ResponseData.Error(messageFailed)
     }

    suspend fun creatUserWithEmailAndPassoword(email: String, password : String): AuthRes<FirebaseUser?> {
       return try {
            val authResult =  fireBaseClient.auth.createUserWithEmailAndPassword(email,password).await()
           AuthRes.Success(authResult.user)

        }catch (e:Exception){
           AuthRes.Error(e.message ?: "Error al crear cuenta")

        }
    }
    suspend fun signInUserWithEmailAndPassoword(email: String, password : String): AuthRes<FirebaseUser?> {

        return try {
            val authResult =  fireBaseClient.auth.signInWithEmailAndPassword(email,password).await()
            AuthRes.Success(authResult.user)

        }catch (e:Exception){
            AuthRes.Error(e.message ?: "Error al crear cuenta")

        }
    }
    suspend fun resetPassword(email: String): AuthRes<Unit> {
        return try {
            fireBaseClient.auth.sendPasswordResetEmail(email).await()
            AuthRes.Success(Unit)
        }catch (e:Exception){
            AuthRes.Error(e.message ?: "Error al restablecer contraseña")

        }
    }



    fun handdleSignInResult(task: Task<GoogleSignInAccount>): AuthRes<GoogleSignInAccount>? {
        return try {
            val account = task.getResult(ApiException::class.java)
            AuthRes.Success(account)
        }catch (e: ApiException){
            AuthRes.Error(e.message ?: "Google Sign-In failed")

        }
    }
    suspend fun signInGoogleCredential(credential: AuthCredential ): AuthRes<FirebaseUser> {
        return try {
            val firebaseUser =  fireBaseClient.auth.signInWithCredential(credential).await()
            firebaseUser.user?.let {
                AuthRes.Success(it)
            }?:throw Exception("Sign in with Goolge failed.")
        }catch (e:Exception){
            AuthRes.Error(e.message ?: "Sign in with Goolge failed.")
        }
    }
    fun signInWithGoogle(context: Context,googleSignInLaucher: ActivityResultLauncher<Intent>){
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        val googleSignInClient: GoogleSignInClient =  GoogleSignIn.getClient(context,gso)
        googleSignInClient.signOut()
        val signInIntent =googleSignInClient.signInIntent
       googleSignInLaucher.launch(signInIntent)
    }

}
sealed class AuthRes< out T>{
    data class Success<T>(val data: T): AuthRes<T>()
    data class Error(val messageError: String): AuthRes<Nothing>()

}