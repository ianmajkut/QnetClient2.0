package com.qnet.qnetclient.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ian.bottomnavigation.ui.home.Model
import com.qnet.qnetclient.data.AuthUser
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

    fun enviarDatos(keyLocal: String?, distancia:String?) {
        repo.agregarCola(keyLocal, distancia)
    }

    fun localesCercanos() {
        repo.localesCercanos()
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

    fun updateUbicacion(latitude: Double?, longitude: Double?) {
        repo.updateUbicacion(latitude, longitude)
    }
}