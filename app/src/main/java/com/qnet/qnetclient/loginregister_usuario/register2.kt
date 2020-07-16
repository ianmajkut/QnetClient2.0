package com.qnet.qnetclient.loginregister_usuario

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.qnet.qnetclient.R

import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlinx.android.synthetic.main.fragment_login_register.buttonNext
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register2.*
import kotlinx.android.synthetic.main.fragment_register2.back_icon


class register2 : Fragment() {
    private var name: String = "No Name"
    private var dni: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        name = register2Args.fromBundle(requireArguments()).nombre
        dni = register2Args.fromBundle(requireArguments()).dni
        return inflater.inflate(R.layout.fragment_register2, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener {
            val action = register2Directions.verificationAction(name, dni)
            findNavController().navigate(action)
        }

    }

}