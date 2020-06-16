package com.qnet.qnetclient.loginregister

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlinx.android.synthetic.main.fragment_login_register.buttonNext
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A simple [Fragment] subclass.
 */
class register : Fragment() {

    private lateinit var viewModel: FirestoreViewModel
    //private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FirestoreViewModel()
        buttonNext.setOnClickListener {
            loadUser()
        }
        back_icon.setOnClickListener {
            findNavController().navigate(R.id.back_action)
        }

    }

    fun loadUser() {
        val name = edtxt_Nombre.text.toString().trim()
        val password = edtxt_Contraseña.text.toString().trim()
        val eMail = edtxt_Email.text.toString().trim()
        val dni = edtxt_DNI.text.toString().trim().toIntOrNull()

        if (name.isNotEmpty() && password.isNotEmpty() && eMail.isNotEmpty() && dni != null) {
            viewModel.createUser(eMail, password)
            viewModel.uploadData(eMail, password, name, dni)
            findNavController().navigate(R.id.next_action)
            Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Error falta algun campo", Toast.LENGTH_SHORT).show()
        }
    }

    /*fun createAccount(email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mAuth.currentUser?.sendEmailVerification()
                } else {

                }
            }
    }

    fun getUser(): FirebaseUser?
    {
        return mAuth.currentUser
    }
    fun emailVerified(): Boolean?
    {
        val emailVerified = getUser()?.isEmailVerified
        return emailVerified
    }*/

}
