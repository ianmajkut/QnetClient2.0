package com.qnet.qnetclient.loginregister_usuario

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_login_register.buttonNext
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.delay

/**
 * A simple [Fragment] subclass.
 */
class register: Fragment() {

    private lateinit var viewModel: FirestoreViewModel
    //private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        Log.i("Verif", "loadUser() register.kt")
        val name = edtxt_Nombre.text.toString().trim()
        val password = edtxt_Contraseña.text.toString().trim()
        val eMail = edtxt_Email.text.toString().trim()
        val dni = edtxt_DNI.text.toString().trim().toIntOrNull()

        if (name.isNotEmpty() && password.isNotEmpty() && eMail.isNotEmpty() && dni != null && password.length>6) {
            crearUsuario(eMail, password, name, dni)
        } else {
            Toast.makeText(activity, "Error falta algun campo", Toast.LENGTH_SHORT).show()
        }
    }

    fun crearUsuario(eMail: String, password: String, name: String, dni: Int){
        viewModel.createUser(eMail, password).observeForever {
            if(it) {
                val action = registerDirections.nextAction(name, dni)
                findNavController().navigate(action)
            }else{
                Toast.makeText(activity, "Error al crear usuario", Toast.LENGTH_SHORT).show()
            }
        }

    }

}