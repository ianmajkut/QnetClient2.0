package com.qnet.qnetclient.applocal.ui.info_local

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_info_local_.*
import kotlinx.android.synthetic.main.row.view.*

class InfoLocal_Fragment : Fragment() {

    private lateinit var layout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_info_local_, container, false)
        return inflater.inflate(R.layout.fragment_info_local_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        btn_editar_nombreLocal.setOnClickListener {
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_cambiarNombreLocalFragment)
        }

        btn_editar_imagenLocal.setOnClickListener {
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_cambiarImagenLocalFragment)
        }

        btn_editar_mailLocal.setOnClickListener {
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_cambiarMailLocalFragment)
        }

        btn_editar_passwordLocal.setOnClickListener {
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_cambiarContraLocalFragment)
        }

        btn_editar_telLocal.setOnClickListener {
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_cambiarTelefonoLocalFragment)
        }

        btn_editar_ubiLocal.setOnClickListener {
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_cambiarUbicacionFragment)
        }

        btn_editar_horarioLocal.setOnClickListener {
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_cambiarHorarioLocalFragment)
        }

        btn_editar_tipoLocal.setOnClickListener {
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_cambiarTipoLocalFragment)
        }

        btn_editar_infoLocal.setOnClickListener {
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_cambiarInfoImportanteFragment)
        }

        btn_cerrarsesionLocal.setOnClickListener {
            alert()
        }
    }

    fun observer() {
        val viewModel = FirestoreViewModel()
        val mAuth = FirebaseAuth.getInstance()
        viewModel.fetchLocal().observeForever {
            val local = it

            Glide.with(this).load(local.image).into(imageView)
            tx_nombreLocal.text = local.title.toString()
            tx_tipoLocal.text = local.descripcion.toString()
            tx_ubiLocal.text = local.direccion.toString()
            tx_horarioLocal.text = local.horario.toString()
            tx_telefonoLocal.text = local.telefono.toString()
            tx_infoLocal.text = local.informacion.toString()
            tx_mailLocal.text = mAuth.currentUser?.email.toString()
        }
    }

    fun alert() {
        val alertDialog = AlertDialog.Builder(requireContext())
        val mAuth = FirebaseAuth.getInstance()
        alertDialog.setTitle("Alerta")
        alertDialog.setMessage("Está a punto de cerrar sesión. ¿Está seguro?")

        alertDialog.setNegativeButton("No") { _, _ -> }
        alertDialog.setPositiveButton("Si") { _, _ ->
            mAuth.signOut()
            val preferences: SharedPreferences =
                requireActivity().getSharedPreferences("RememberMe", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putBoolean("remember", false)
            editor.putString("name", null)
            editor.putString("password", null)
            editor.apply()
            findNavController().navigate(R.id.action_infoLocal_Fragment_to_local_o_usuario2)
        }
        alertDialog.show()
    }
}