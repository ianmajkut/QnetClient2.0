package com.qnet.qnetclient.appusuario.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_cambiar_contra_usuario.*
import kotlinx.android.synthetic.main.fragment_cambiar_mail_usuario.buttonNext

class CambiarContraUsuarioFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout= inflater.inflate(R.layout.fragment_cambiar_contra_usuario, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener {
            val newPassword = edtxt_Contraseña.text?.trim().toString()
            if (newPassword.isNotEmpty()) {
                cambiarPassword(newPassword).observeForever {
                    if (it) {
                        Toast.makeText(
                            requireContext(),
                            "Contraseña actualizada",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.contra_to_settings)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Por favor, complete los campos de informacion", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cambiarPassword(newPassword: String): LiveData<Boolean> {

        val mutableData = MutableLiveData<Boolean>()

        mAuth = FirebaseAuth.getInstance()
        val preferences: SharedPreferences =
            requireActivity().getSharedPreferences("RememberMe", Context.MODE_PRIVATE)
        val currentEmail: String? = preferences.getString("email", "")
        val currentPassword: String? = preferences.getString("password", "")

        val credential: AuthCredential = EmailAuthProvider.getCredential(
            currentEmail!!,
            currentPassword!!
        )
        mAuth.currentUser?.reauthenticate(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                mAuth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener {
                    mutableData.value = it.isSuccessful
                }
            }
        }
        return mutableData
    }
}