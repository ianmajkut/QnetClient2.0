package com.qnet.qnetclient.loginregister_usuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import com.qnet.qnetclient.R

import kotlinx.android.synthetic.main.fragment_verification.*


lateinit var mAuth: FirebaseAuth

class verification : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        verification.setOnClickListener{
            if(currentUser != null) {
                if(currentUser.isEmailVerified) {
                    findNavController().navigate(R.id.menu_action)
                }
            }
        }
    }

}