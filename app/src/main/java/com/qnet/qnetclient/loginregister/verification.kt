package com.qnet.qnetclient.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.qnet.qnetclient.R
import com.qnet.qnetclient.loginregister.verification
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlinx.android.synthetic.main.fragment_login_register.buttonNext
import kotlinx.android.synthetic.main.fragment_register.*

import kotlinx.android.synthetic.main.fragment_verification.*


class verification : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verification.setOnClickListener{
            findNavController().navigate(R.id.menu_action)
        }


    }

}
