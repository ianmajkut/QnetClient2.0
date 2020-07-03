package com.qnet.qnetclient.loginregister_usuario

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel

import kotlinx.android.synthetic.main.fragment_verification.*


lateinit var mAuth: FirebaseAuth

class verification : Fragment() {
    private var name: String = "No Name"
    private var dni: Int = 0
    private lateinit var viewModel: FirestoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = FirestoreViewModel()
        name = verificationArgs.fromBundle(requireArguments()).nombre
        dni = verificationArgs.fromBundle(requireArguments()).dni
        Log.i("Verif", name)
        Log.i("Verif", dni.toString())
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        verification.setOnClickListener{
//            if(currentUser != null) {
//                if(currentUser.isEmailVerified) {
//                    findNavController().navigate(R.id.menu_action)
//                }
//            }
            viewModel.uploadData(name, dni)
            findNavController().navigate(R.id.menu_action)
        }
    }

}