package com.qnet.qnetclient.data.repo

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.SetOptions
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
            .addOnSuccessListener {
                Log.i(TAG, "User successfully created.")
            }
            .addOnFailureListener {
                Log.w(TAG, "Error adding document", it)
            }
    }

    fun agregarCola(keyLocal: String?, dist:String?): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        functions = FirebaseFunctions.getInstance()

        val distancia = dist?.toLong()
        val data = hashMapOf(
            "keyLocal" to keyLocal,
            "distancia" to distancia,
            "push" to true
        )
        functions.getHttpsCallable("agregarCola")
            .call(data).addOnCompleteListener {task ->
                mutableData.value = task.isSuccessful
            }
        return mutableData
    }

    fun updateUbicacion(latitude: Double?, longitude: Double?): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        mAuth = FirebaseAuth.getInstance()

        val data = hashMapOf(
            "ubicacion" to GeoPoint(latitude!!, longitude!!)
        )
        db.collection("users").document(mAuth.currentUser?.uid.toString())
            .set(data, SetOptions.merge()).addOnCompleteListener {
                mutableData.value = it.isSuccessful
            }
        return mutableData
    }

    fun localesCercanos(): LiveData<Boolean> {
        functions = FirebaseFunctions.getInstance()
        val mutableData = MutableLiveData<Boolean>()

        functions.getHttpsCallable("iniciarApp").call()
            .addOnCompleteListener { task ->
                mutableData.value = task.isSuccessful
            }
        return mutableData
    }

    fun getUsuarioData(): LiveData<MutableList<Model>> {
        val mutableData = MutableLiveData<MutableList<Model>>()
        val listData = mutableListOf<Model>()

        db.document("users/${mAuth.currentUser?.uid}").get()
            .addOnSuccessListener { result ->
                val nombre = result.getString("name")
                val email = mAuth.currentUser?.email
            }
    }

    fun getLocalData(): LiveData<MutableList<Model>> {
        val mutableData = MutableLiveData<MutableList<Model>>()
        val listData = mutableListOf<Model>()
        getLocalesReference().observeForever{
            for(reference in it)  {

                db.document("locales/${reference.keyLocal}").get().addOnSuccessListener { result ->

                    val title = result.getString("title")
                    val descripcion = result.getString("descripcion")
                    val num = result.getLong("queueNumber").toString()
                    val image = result.getString("image")
                    val local = Model(title, descripcion, num, reference.distancia, image,null,reference.keyLocal)
                    listData.add(local)
                    mutableData.value = listData

                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
                if (mutableData == null && aux < 3) {
                    aux++
                    getLocalData()
                }
                aux=0
            }
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
        if (mutableData == null && aux < 5) {
            aux++
            getLocalesReference()
        }
        return mutableData
    }

    fun getMisColas():LiveData<MutableList<Model>> {
        val mutableData = MutableLiveData<MutableList<Model>>()
        var mutableReference = mutableListOf<References>()
        val listData = mutableListOf<Model>()
        getMisColasReference().observeForever{
            for(reference in it)
            {
                db.document("locales/${reference.keyLocal}").get().addOnSuccessListener {result ->
                    val title = result.getString("title")
                    val descripcion = result.getString("descripcion")
                    val num = result.getLong("queueNumber").toString()
                    val image = result.getString("image")
                    val local = Model(title, descripcion, num, reference.distancia, image,reference.posicion,reference.keyLocal)
                    listData.add(local)
                    mutableData.value = listData
                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
                if (mutableData == null && aux < 5) {
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
                val distancia = document.getLong("distancia").toString()
                listData.add(
                    References(
                        keyLocal,
                        posicion,
                        distancia
                    )
                )
            }
            mutableData.value = listData
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error getting document", e)
        }
        if (mutableData == null && aux < 5) {
            aux++
            getLocalData()
        }
        return mutableData
    }
}