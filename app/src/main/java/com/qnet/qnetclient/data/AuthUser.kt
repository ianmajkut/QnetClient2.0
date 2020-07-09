package com.qnet.qnetclient.data

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import org.jetbrains.annotations.NotNull
import kotlin.coroutines.coroutineContext

class AuthUser {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var aux:Boolean = false

    fun createAccount(email: String, password: String) {
        Log.i("Verif", "createUser() AuthUser.kt")
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.i("Verif", "E-Mail sent to ${mAuth.currentUser!!.email}")
                        } else {
                            Log.i("Verif", "Exception: ", task.exception)
                        }
                    }
                } else {

                }
            }
    }

    fun singInAccount(email: String,password: String):Boolean
    {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                aux = task.isSuccessful

            }
        return aux
    }
}