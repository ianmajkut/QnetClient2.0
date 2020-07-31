package com.qnet.qnetclient.applocal.ui.info_local

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_cambiar_mail_usuario.*
import kotlinx.android.synthetic.main.fragment_verificar_nuevo_mail_usuario.*


class VerificarNuevaContraLocalFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout= inflater.inflate(R.layout.fragment_verificar_nueva_contra_local, container, false)

        return layout
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verification.setOnClickListener {
            findNavController().navigate(R.id.action_verificarNuevaContraLocalFragment_to_infoLocal_Fragment)
        }

    }


}