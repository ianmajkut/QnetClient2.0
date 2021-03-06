package com.qnet.qnetclient.loginregister_usuario

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel

import kotlinx.android.synthetic.main.fragment_verification.*

class verification : Fragment() {
    private var name: String = "No Name"
    private var dni: Int = 0
    private lateinit var viewModel: FirestoreViewModel
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = FirestoreViewModel()
        name = verificationArgs.fromBundle(requireArguments()).nombre
        dni = verificationArgs.fromBundle(requireArguments()).dni
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val user: FirebaseUser = mAuth.currentUser!!
        verification.setOnClickListener{
            Log.i("Verif", mAuth.currentUser?.isEmailVerified.toString())
            user.reload()
            if (user.isEmailVerified) {
                Log.i("Verif", "verified")
                Toast.makeText(activity, "E-Mail verified", Toast.LENGTH_SHORT).show()
                viewModel.uploadUserData(name, dni)
                findNavController().navigate(R.id.menu_action)
            } else {
                Toast.makeText(activity, "E-Mail not verified", Toast.LENGTH_SHORT).show()
                Log.i("Verif", "not verified")
            }
        }
    }
}