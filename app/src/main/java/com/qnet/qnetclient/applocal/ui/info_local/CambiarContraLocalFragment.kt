package com.qnet.qnetclient.applocal.ui.info_local

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
import kotlinx.android.synthetic.main.fragment_cambiar_contra_local.*
import kotlinx.android.synthetic.main.fragment_cambiar_contra_local.back_icon
import kotlinx.android.synthetic.main.fragment_cambiar_contra_local.buttonNext


class CambiarContraLocalFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambiar_contra_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_icon.setOnClickListener {
            findNavController().navigate(R.id.action_cambiarNombreLocalFragment_to_infoLocal_Fragment)
        }

        buttonNext.setOnClickListener {
            val newPassword = edtxt_ContraseñaLocal.text?.trim().toString()
            if (newPassword.isNotEmpty()) {
                cambiarPassword(newPassword).observeForever {
                    if (it) {
                        Toast.makeText(
                            requireContext(),
                            "Contraseña actualizada",
                            Toast.LENGTH_SHORT
                        ).show()
                        val preferences: SharedPreferences =
                            requireActivity().getSharedPreferences(
                                "RememberMe",
                                Context.MODE_PRIVATE
                            )
                        val editor: SharedPreferences.Editor = preferences.edit()
                        val currentEmail: String? = preferences.getString("email", "")
                        editor.putString("password", newPassword)
                        editor.putString("email", currentEmail)
                        editor.apply()
                        findNavController().navigate(R.id.action_cambiarContraLocalFragment_to_infoLocal_Fragment)
                    }else {
                        Toast.makeText(
                            requireContext(),
                            "No se pudo actualizar su Contraseña",
                            Toast.LENGTH_SHORT
                        ).show()
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