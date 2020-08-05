package com.qnet.qnetclient.applocal.ui.info_local

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
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
        layout= inflater.inflate(R.layout.fragment_info_local_, container, false)
        observer()

        return inflater.inflate(R.layout.fragment_info_local_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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


        }

    }
    fun observer(){
        val viewModel = FirestoreViewModel()
        val mAuth = FirebaseAuth.getInstance()
        viewModel.fetchLocal().observeForever{
            val local = it
            val image:ImageView = layout.findViewById(R.id.imageView)
            val titulo: TextView = layout.findViewById(R.id.tx_nombreLocal)
            val descripcion: TextView = layout.findViewById(R.id.tx_tipoLocal)
            val ubicacion: TextView = layout.findViewById(R.id.tx_ubiLocal)
            val horario: TextView = layout.findViewById(R.id.tx_horarioLocal)
            val telefono: TextView = layout.findViewById(R.id.tx_telefonoLocal)
            val mini_Desc: TextView = layout.findViewById(R.id.tx_infoLocal)
            val email :TextView = layout.findViewById(R.id.tx_mailLocal)


            Glide.with(this).load(local.image).into(image)
            titulo.text = local.title
            descripcion.text = local.descripcion
            ubicacion.text = local.direccion
            horario.text = local.horario
            telefono.text = local.telefono
            mini_Desc.text = local.informacion
            email.text = mAuth.currentUser?.email
        }
    }
}