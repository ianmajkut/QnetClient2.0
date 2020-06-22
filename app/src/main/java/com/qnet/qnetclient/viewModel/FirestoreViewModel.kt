package com.qnet.qnetclient.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.qnet.qnetclient.data.AuthUser
import com.qnet.qnetclient.data.FirebaseRepo

class FirestoreViewModel : ViewModel(){

    private val repo = FirebaseRepo()
    private val repoAuth = AuthUser()

    fun createUser(eMail:String,password:String)
    {
        repoAuth.createAccount(eMail,password)
    }

    fun uploadData(name: String,dni: Int)
    {
        repo.uploadData(name, dni)
    }

    fun singInUser(eMail: String,password: String):Boolean
    {
        return repoAuth.singInAccount(eMail,password)
    }



}