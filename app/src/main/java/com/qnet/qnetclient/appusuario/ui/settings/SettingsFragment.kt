package com.ian.bottomnavigation.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_forget.*
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlinx.android.synthetic.main.fragment_login_register.buttonNext
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {
    private var mAuth = FirebaseAuth.getInstance()
    private var viewModel = FirestoreViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout=inflater.inflate(R.layout.fragment_settings, container, false)

        setUserData()

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_editar_mail.setOnClickListener {

            findNavController().navigate(R.id.settings_to_mail)

        }
        btn_editar_contra.setOnClickListener {

            findNavController().navigate(R.id.settings_to_contra)

        }

        btn_cerrarsesion.setOnClickListener {
            alerta()
        }
    }

    fun setUserData() {
        viewModel.fetchUserData().observe(viewLifecycleOwner, Observer {
            tv_nombre.text = ("Nombre: " + it.name.toString())
            tv_mail.text = ("E-Mail: " + it.email.toString())
        })
    }

    fun alerta(){
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Alerta")
        alertDialog.setMessage("Está a punto de cerrar sesión. ¿Está seguro?")

        alertDialog.setNegativeButton("No") { _, _ ->

        }
        alertDialog.setPositiveButton("Si") { _, _ ->
            mAuth.signOut()
            val preferences: SharedPreferences =
                requireActivity().getSharedPreferences("RememberMe", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putBoolean("remember", false)
            editor.putString("name", null)
            editor.putString("password", null)
            editor.apply()
            findNavController().navigate(R.id.action_navigation_settings_to_local_o_usuario)
        }
        alertDialog.show()

    }

}