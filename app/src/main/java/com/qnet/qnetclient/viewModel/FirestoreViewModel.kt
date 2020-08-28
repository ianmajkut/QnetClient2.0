package com.qnet.qnetclient.viewModel

import android.net.Uri
import android.view.Display
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ian.bottomnavigation.ui.home.Model
import com.qnet.qnetclient.appusuario.ui.settings.SettingsModel
import com.qnet.qnetclient.data.AuthUser
import com.qnet.qnetclient.data.classes.Usuario
import com.qnet.qnetclient.data.repo.FirebaseRepo
import com.qnet.qnetclient.loginregister_local.InfoRegister

class FirestoreViewModel : ViewModel(){
    private val repo = FirebaseRepo()
    private val repoAuth = AuthUser()

    fun createUser(eMail: String, password: String):LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        repoAuth.createAccount(eMail, password).observeForever{
            mutableData.value = it
        }
        return mutableData
    }

    fun uploadUserData(name: String, dni: Int) {
        repo.uploadUserData(name, dni)
    }

    fun singInUser(eMail: String,password: String):LiveData<Int> {
        val mutableData = MutableLiveData<Int>()
        repoAuth.singInAccount(eMail,password).observeForever{
            if(it) {
                repo.isLocal().observeForever { result -> //1 -> es Usuario
                    if(result!=null) {
                        if (result) {
                            mutableData.value = 2   // 2 -> es Local
                        } else {
                            mutableData.value = 1 // 1 -> es Usuario
                        }
                    }
                }
            }else{
                mutableData.value = 0 // 0 -> Error
            }
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

    fun updateUbicacion(latitude: Double?, longitude: Double?, llamadaUsusario: Boolean): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        repo.updateUbicacion(latitude, longitude, llamadaUsusario).observeForever {
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

    fun fetchLocal():LiveData<Model>{
        val mutableData = MutableLiveData<Model>()

        repo.getLocal().observeForever{
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

    fun sacarUser(user:String?, local: String?, llamadaLocal: Boolean): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        repo.sacarUser(user, local, llamadaLocal).observeForever {
            if (it) {
                mutableData.value = it
            }
        }
        return mutableData
    }


    fun loadImage(uri: Uri?, info: InfoRegister):LiveData<Boolean>{
        val mutableData = MutableLiveData<Boolean>()
        repo.uploadImage(uri,info).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun refreshToken() {
        repo.refreshToken()
    }

    fun sacarUser(reference:String?):LiveData<Usuario>{
        val mutableData = MutableLiveData<Usuario>()
        repo.sacarUser(reference).observeForever{
            mutableData.value = it
        }
        return mutableData
    }

    fun changeData(campo: String, info: Any): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        repo.changeData(campo, info).observeForever{
            mutableData.value = it
        }
        return mutableData
    }
    fun changeImage(uri: Uri?):LiveData<Boolean>{
        val mutableData  = MutableLiveData<Boolean>()
        repo.changeImage(uri).observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}