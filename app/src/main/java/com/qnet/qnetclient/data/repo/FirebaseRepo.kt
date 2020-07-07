package com.qnet.qnetclient.data.repo

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.ian.bottomnavigation.ui.home.Model

class FirebaseRepo {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var functions: FirebaseFunctions
    private var aux = 0
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
    fun getLocalData(): LiveData<MutableList<Model>> {
        val mutableData = MutableLiveData<MutableList<Model>>()
        db.collection("locales").get().addOnSuccessListener {result ->
            val listData = mutableListOf<Model>()
            for (document in result){
                val title = document.getString("title")
                val descripcion = document.getString("descripcion")
                val num = document.getString("title")
                val dist = document.getString("descripcion")
                val image = document.getString("image")
                val local = Model(title, descripcion, num, dist, image)
                listData.add(local)
            }
            mutableData.value = listData
        }
        if (mutableData == null&&aux<5) {
            aux++
            getLocalData()
        }
        aux=0
        return mutableData
    }
}