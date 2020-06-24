package com.qnet.qnetclient.viewModel

import androidx.lifecycle.ViewModel
import com.qnet.qnetclient.domain.FirestoreUseCase
import com.qnet.qnetclient.data.AuthUser
import com.qnet.qnetclient.data.repo.FirebaseRepo

class FirestoreViewModel : ViewModel(){
    private val repo = FirebaseRepo()
    private val repoAuth = AuthUser()
    private val firestoreUseCase = FirestoreUseCase()

    fun createUser(eMail:String,password:String) {
        repoAuth.createAccount(eMail,password)
    }

    fun uploadData(name: String,dni: Int) {
        repo.uploadData(name, dni)
    }

    fun singInUser(eMail: String,password: String):Boolean {
        return repoAuth.singInAccount(eMail,password)
    }

    fun enviarDatos(keyLocal: String) {
        firestoreUseCase.enviarKeyLocal(keyLocal)
    }
}