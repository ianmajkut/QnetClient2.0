package com.qnet.qnetclient.data.repo

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions

class FirebaseRepo {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var functions: FirebaseFunctions
    private lateinit var mAuth: FirebaseAuth

    fun uploadData(name:String,dni:Int) {
        mAuth = FirebaseAuth.getInstance()
        val user = hashMapOf(
            "name" to name,
            "dni" to dni
        )
        db.document("users/${mAuth.currentUser?.uid}")
            .set(user as Map<String, Any>)
            .addOnSuccessListener { documentReference ->
//                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun agregarCola(keyLocal: String): Task<String> {
        functions = FirebaseFunctions.getInstance()
        val data = hashMapOf(
            "keyLocal" to keyLocal,
            "push" to true
        )
        return functions
            .getHttpsCallable("agregarCola")
            .call(data)
            .continueWith { task ->
                task.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.i("Cloud Functions", "Successful")
                    } else {
                        Log.i("Cloud Functions", "Failure")
                        Log.i("Cloud Functions", task.exception.toString())
                    }
                }
                val result = task.result?.data.toString()
                result
            }
    }
}