package com.qnet.qnetclient.data

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseRepo {

    private val db = FirebaseFirestore.getInstance()

    fun uploadData(email: String,password: String,name:String,dni:Int)
    {
        val user = hashMapOf(
            "email" to email,
            "password" to password,
            "name" to name,
            "dni" to dni
        )
        db.collection("users")
            .add(user as Map<String, Any>)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }






}