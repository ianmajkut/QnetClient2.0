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
import kotlinx.android.synthetic.main.fragment_cambiar_mail_usuario.*


class CambiarMailUsuarioFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout= inflater.inflate(R.layout.fragment_cambiar_mail_usuario, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonNext.setOnClickListener {
            val newEmail = edtxt_Email.text?.trim().toString()
            if (newEmail.isNotEmpty()) {
                cambiarEmail(newEmail).observeForever {
                    if (it) {
                        Toast.makeText(
                            requireContext(),
                            "E-Mail actualizado",
                            Toast.LENGTH_SHORT
                        ).show()
                        val preferences: SharedPreferences =
                            requireActivity().getSharedPreferences(
                                "RememberMe",
                                Context.MODE_PRIVATE
                            )
                        val editor: SharedPreferences.Editor = preferences.edit()
                        val currentPassword: String? = preferences.getString("password", "")
                        editor.putString("email", newEmail)
                        editor.putString("password", currentPassword)
                        editor.apply()
                        findNavController().navigate(R.id.mail_to_settings)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No se pudo actualizar su E-Mail",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Por favor, complete los campos de informacion", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cambiarEmail(newEmail: String): LiveData<Boolean> {

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
                mAuth.currentUser?.updateEmail(newEmail)?.addOnCompleteListener {
                    mutableData.value = it.isSuccessful
                }
            }
        }
        return mutableData
    }
}