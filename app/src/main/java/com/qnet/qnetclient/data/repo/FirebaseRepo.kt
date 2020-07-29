package com.qnet.qnetclient.data.repo

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.SetOptions
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage
import com.ian.bottomnavigation.ui.home.Model
import com.qnet.qnetclient.appusuario.ui.settings.SettingsModel
import com.qnet.qnetclient.data.classes.References
import com.qnet.qnetclient.data.classes.ReferenceLocalesCercanos
import com.qnet.qnetclient.data.classes.ReferenceUsuarios
import com.qnet.qnetclient.data.classes.Usuario
import com.qnet.qnetclient.loginregister_local.InfoRegister
import java.util.*
import kotlin.collections.ArrayList

class FirebaseRepo {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var functions: FirebaseFunctions
    private lateinit var mAuth: FirebaseAuth
    private var aux = 0

    fun uploadData(name: String, dni: Int) {
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

    fun uploadImage(uri: Uri?,info:InfoRegister):LiveData<Boolean>{
        val mutableData = MutableLiveData<Boolean>()
        if (uri == null) return mutableData
        val filename = UUID.randomUUID().toString()
        val ref= FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(uri).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                referenceImage(it.toString(),info).observeForever{result ->
                    mutableData.value = result
                }
            }
        }.addOnFailureListener{
            mutableData.value= false
        }
        return mutableData
    }

    private fun referenceImage(path:String?,info: InfoRegister):LiveData<Boolean>{
        val mutableData  = MutableLiveData<Boolean>()
        mAuth = FirebaseAuth.getInstance()

        val data = hashMapOf(
            "image" to path,
            "title" to  info.nombre,
            "direccion" to info.ubicacion,
            "horario" to info.horario,
            "descripcion" to info.tipo,
            "informacion" to info.informacion,
            "queueNumber" to Int,
            "queuedPeople" to ArrayList<String>()
        )

        db.document("locales/${mAuth.currentUser?.uid}")
            .set(data)
            .addOnSuccessListener {
                mutableData.value = true
            }
            .addOnFailureListener{
                mutableData.value = false
            }

        return mutableData
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
            .call(data).addOnCompleteListener { task ->
                mutableData.value = task.isSuccessful
            }
        return mutableData
    }

    fun sacarUser(user: String?, local: String?, llamadaLocal: Boolean): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        functions = FirebaseFunctions.getInstance()

        val data = hashMapOf(
            "keyUsuario" to user,
            "keyLocal" to local,
            "llamadaLocal" to llamadaLocal,
            "push" to true
        )
        functions.getHttpsCallable("eliminarCola")
            .call(data).addOnCompleteListener {
                mutableData.value = it.isSuccessful
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

    fun getUsuario(): LiveData<SettingsModel> {
        val mutableData = MutableLiveData<SettingsModel>()
        mAuth = FirebaseAuth.getInstance()

        db.document("users/${mAuth.currentUser?.uid}").get()
            .addOnSuccessListener { result ->
                val nombre = result.getString("name")
                val email = mAuth.currentUser?.email
                val ubicacion = result.getGeoPoint("ubicacion")
                val latitud = ubicacion?.latitude
                val longitud = ubicacion?.longitude
                val usuario = SettingsModel(nombre, email, latitud.toString(), longitud.toString())
                mutableData.value = usuario
            }.addOnFailureListener {
                Log.i("getUsuario", "Failed: $it")
            }
        return mutableData
    }

    fun getLocalData(): LiveData<MutableList<Model>> {
        val mutableData = MutableLiveData<MutableList<Model>>()
        val listData = mutableListOf<Model>()

        getLocalesReference().observeForever {
            for (reference in it) {
                db.document("locales/${reference.keyLocal}").get().addOnSuccessListener { result ->

                    val title = result.getString("title")
                    val descripcion = result.getString("descripcion")
                    val num = result.getLong("queueNumber").toString()
                    val image = result.getString("image")
                    val ubicacion = result.getGeoPoint("ubicacion")
                    val latLocal = ubicacion?.latitude.toString()
                    val longLocal = ubicacion?.longitude.toString()
                    val local = Model(
                        title,
                        descripcion,
                        num,
                        reference.distancia,
                        image,
                        null,
                        reference.keyLocal,
                        latLocal,
                        longLocal
                    )
                    listData.add(local)
                    mutableData.value = listData

                }.addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

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
                    val local = Model(title, descripcion, num, reference.distancia, image,reference.posicion,reference.keyLocal, null, null)
                    listData.add(local)
                    mutableData.value = listData
                }.addOnFailureListener { e ->
                    mutableData.value = null
                    Log.w(TAG, "Error adding document", e)
                }
                aux = 0
            }
        }
        return mutableData
    }

    private fun getMisColasReference(): LiveData<MutableList<References>> {
        mAuth = FirebaseAuth.getInstance()
        val mutableData = MutableLiveData<MutableList<References>>()
        db.collection("users/${mAuth.currentUser?.uid}/misColas").get()
            .addOnSuccessListener { reference ->
                val listData = mutableListOf<References>()
                for (document in reference) {
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
        return mutableData
    }

    fun getUsers(): LiveData<MutableList<Usuario>> {
        val mutableData = MutableLiveData<MutableList<Usuario>>()
        val listData = mutableListOf<Usuario>()

        getUsersReference().observeForever {
            if (it.queueNumber != null) {
                aux = 0
                for (reference in it.queuedPeople) {
                    aux++
                    db.document("users/${reference}").get().addOnSuccessListener { result ->
                        val name = result.getString("name")
                        val usuario = Usuario(name, aux)
                        listData.add(usuario)
                        mutableData.value = listData
                    }.addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
                }
            }
        }
        return mutableData
    }

    fun getUsersReference(): LiveData<ReferenceUsuarios> {
        mAuth = FirebaseAuth.getInstance()
        val location = mAuth.currentUser?.uid
        val mutableData = MutableLiveData<ReferenceUsuarios>()
        db.document("locales/${location}").get().addOnSuccessListener { result ->
            val queuedPeople = result.data?.get("queuedPeople")
            val queueNumber = result.getLong("queueNumber").toString()
            mutableData.value = ReferenceUsuarios(queuedPeople as ArrayList<String>, queueNumber)
        }
        return mutableData
    }

    fun refreshToken() {
        mAuth = FirebaseAuth.getInstance()
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (it.isSuccessful) {
                val data = hashMapOf(
                    "token" to it.result?.token
                )
                db.document("users/${mAuth.currentUser?.uid}").set(
                    data, SetOptions.merge()
                )
            }
        }
    }
}