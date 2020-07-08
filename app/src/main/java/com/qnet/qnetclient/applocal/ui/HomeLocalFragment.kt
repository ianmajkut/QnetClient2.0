package com.qnet.qnetclient.applocal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_home_local.*

class HomeLocalFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filalocal.setOnClickListener {
            findNavController().navigate(R.id.action_homeLocalFragment_to_filaLocalFragment)
        }
        lectorcodigoQR.setOnClickListener {
            findNavController().navigate(R.id.action_homeLocalFragment_to_lectorQr_Fragment)
        }
        informacionlocal.setOnClickListener {
            findNavController().navigate(R.id.action_homeLocalFragment_to_infoLocal_Fragment)
        }
    }
}