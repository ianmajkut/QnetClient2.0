package com.qnet.qnetclient.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ian.bottomnavigation.ui.home.Model
import com.qnet.qnetclient.appusuario.ui.settings.SettingsModel
import com.qnet.qnetclient.data.AuthUser
import com.qnet.qnetclient.data.classes.Usuario
import com.qnet.qnetclient.data.repo.FirebaseRepo

class FirestoreViewModel : ViewModel(){
    private val repo = FirebaseRepo()
    private val repoAuth = AuthUser()


    fun createUser(eMail: String, password: String) {
        Log.i("Verif", "createUser() FirestoreViewModel.kt")
        repoAuth.createAccount(eMail,password)
    }

    fun uploadData(name: String,dni: Int) {
        repo.uploadData(name, dni)
    }

    fun singInUser(eMail: String,password: String):LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        repoAuth.singInAccount(eMail,password).observeForever{
            mutableData.value = it
        }
        return mutableData
    }

    fun localesCercanos(): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        repo.localesCercanos().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun updateUbicacion(latitude: Double?, longitude: Double?): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        repo.updateUbicacion(latitude, longitude).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun enviarDatos(keyLocal: String?, distancia:String?): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        repo.agregarCola(keyLocal, distancia).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun fetchUserData(): LiveData<SettingsModel> {
        val mutableData = MutableLiveData<SettingsModel>()
        repo.getUsuario().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun fetchLocalData(): LiveData<MutableList<Model>> {
        val mutableData = MutableLiveData<MutableList<Model>>()
        repo.getLocalData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }

    fun fetchMisColas():LiveData<MutableList<Model>>{
        val mutableData = MutableLiveData<MutableList<Model>>()
        repo.getMisColas().observeForever{
            mutableData.value = it
        }
        return mutableData
    }

    fun fetchUsuarios():LiveData<MutableList<Usuario>>{
        val mutableData = MutableLiveData<MutableList<Usuario>>()
        repo.getUsers().observeForever{
            mutableData.value = it
        }
        return mutableData
    }

    fun sacarUser(user:String?) {
        repo.sacarUser(user)
    }

}