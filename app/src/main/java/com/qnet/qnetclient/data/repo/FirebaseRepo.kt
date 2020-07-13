package com.qnet.qnetclient.data.repo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.ian.bottomnavigation.ui.home.Model
import com.qnet.qnetclient.data.classes.References
import com.qnet.qnetclient.data.classes.ReferenceLocalesCercanos


class FirebaseRepo {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var functions: FirebaseFunctions
    private lateinit var mAuth: FirebaseAuth
    private var aux = 0


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
                val num = document.getString("cola")
                val dist = document.getString("dist")
                val image = document.getString("image")
                val local = Model(title, descripcion, num, dist, image,null)
                listData.add(local)
            }
            mutableData.value = listData
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
        if (mutableData == null&&aux<3) {
            aux++
            getLocalData()
        }
        aux=0
        return mutableData
    }

    fun getMisColas():LiveData<MutableList<Model>> {
        val mutableData = MutableLiveData<MutableList<Model>>()
        var mutableReference = mutableListOf<References>()
        getMisColasReference().observeForever{
            for(reference in it)
            {
                db.document("locales/${reference.keyLocal}").get().addOnSuccessListener {result ->
                    val listData = mutableListOf<Model>()
                    val title = result.getString("title")
                    val descripcion = result.getString("descripcion")
                    val num = result.getString("cola")
                    val dist = result.getString("dist")
                    val image = result.getString("image")
                    val local = Model(title, descripcion, num, dist, image,reference.posicion)
                    listData.add(local)

                    mutableData.value = listData
                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
                if (mutableData == null&&aux<5) {
                    aux++
                    getLocalData()
                }
                aux=0

            }
        }
        return mutableData
    }



    private fun getMisColasReference():LiveData<MutableList<References>>  {
        mAuth = FirebaseAuth.getInstance()
        val mutableData = MutableLiveData<MutableList<References>>()
        db.collection("users/${mAuth.currentUser?.uid}/misColas").get().addOnSuccessListener { reference ->
            val listData = mutableListOf<References>()
            for (document in reference){
                val keyLocal = document.getString("keyLocal")
                val posicion = document.getLong("posicion").toString()
                listData.add(
                    References(
                        keyLocal,
                        posicion
                    )
                )
            }
            mutableData.value = listData
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error getting document", e)
        }
        if (mutableData == null&&aux<5) {
            aux++
            getLocalData()
        }
        return mutableData
    }

    private fun getLocalesReference():LiveData<MutableList<ReferenceLocalesCercanos>>{
        mAuth = FirebaseAuth.getInstance()
        val mutableData = MutableLiveData<MutableList<ReferenceLocalesCercanos>>()
        db.collection("users/${mAuth.currentUser?.uid}/localesCercanos").get().addOnSuccessListener { reference ->
            val listData = mutableListOf<ReferenceLocalesCercanos>()
            for (document in reference){
                val keyLocal = document.getString("keyLocal")
                val distancia = document.getLong("distancia").toString()
                listData.add(
                    ReferenceLocalesCercanos(
                        keyLocal,
                        distancia
                    )
                )
            }
            mutableData.value = listData
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error getting document", e)
        }
        if (mutableData == null&&aux<5) {
            aux++
            getLocalesReference()
        }
        return mutableData
    }
}