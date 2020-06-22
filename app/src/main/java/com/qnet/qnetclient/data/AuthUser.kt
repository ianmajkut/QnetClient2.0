package com.qnet.qnetclient.data

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth


class AuthUser {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var aux:Boolean = false

    fun createAccount(email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mAuth.currentUser?.sendEmailVerification()
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