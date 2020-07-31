package com.qnet.qnetclient.applocal.ui.info_local

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_info_local_.*


class InfoLocal_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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
}