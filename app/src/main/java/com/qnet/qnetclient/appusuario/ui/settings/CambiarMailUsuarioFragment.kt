package com.qnet.qnetclient.appusuario.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_cambiar_mail_usuario.*
import kotlinx.android.synthetic.main.fragment_settings.*


class CambiarMailUsuarioFragment : Fragment() {


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

            findNavController().navigate(R.id.mail_to_verification)

        }

        back_icon.setOnClickListener {

            findNavController().navigate(R.id.mail_to_settings)

        }

    }

}