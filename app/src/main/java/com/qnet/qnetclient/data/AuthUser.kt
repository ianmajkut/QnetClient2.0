package com.qnet.qnetclient.data

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import org.jetbrains.annotations.NotNull
import kotlin.coroutines.coroutineContext

class AuthUser {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var aux: Boolean = false

    fun createAccount(email: String, password: String):LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            mutableData.value = result.isSuccessful
                        } else {
                            mutableData.value = result.isSuccessful
                        }
                    }
                } else {
                    mutableData.value = task.isSuccessful
                }
            }

        return mutableData
    }

    fun singInAccount(email: String,password: String): LiveData<Boolean>
    {
        val mutableData = MutableLiveData<Boolean>()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                mutableData.value = task.isSuccessful
            }
        return mutableData
    }
}