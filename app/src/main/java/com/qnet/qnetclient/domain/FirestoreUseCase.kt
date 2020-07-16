package com.qnet.qnetclient.domain

import com.qnet.qnetclient.data.repo.FirebaseRepo

class FirestoreUseCase {
    val repo = FirebaseRepo()

    fun enviarKeyLocal(keyLocal: String?,distancia:String?) {
        repo.agregarCola(keyLocal,distancia)
    }
}