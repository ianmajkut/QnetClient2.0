package com.qnet.qnetclient.loginregister

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_login_register.*


class login_register : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_register, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            buttonNew.setOnClickListener{
                findNavController().navigate(R.id.next_action)
            }
            buttonForget.setOnClickListener{
            findNavController().navigate(R.id.forget_action)
            }
            buttonNext.setOnClickListener{
            findNavController().navigate(R.id.menu_principal_action)
            }

    }

}
