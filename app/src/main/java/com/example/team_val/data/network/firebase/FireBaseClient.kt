package com.example.team_val.data.network.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FireBaseClient @Inject constructor(){
    val auth: FirebaseAuth get() = FirebaseAuth.getInstance()
    val db = Firebase.firestore
}