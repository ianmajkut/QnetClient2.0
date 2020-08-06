package com.qnet.qnetclient.applocal.ui.info_local

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.rotationMatrix
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_cambiar_mail_local.*


class CambiarMailLocalFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambiar_mail_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_icon.setOnClickListener {

            findNavController().navigate(R.id.action_cambiarMailLocalFragment_to_infoLocal_Fragment)

        }
        buttonNext.setOnClickListener {
            changeMail()

        }

    }
    fun changeMail(){
        if(edtxt_mail.text.toString().isNotEmpty()){
            mAuth.currentUser?.updateEmail(edtxt_mail.text.toString())?.addOnSuccessListener {
                findNavController().navigate(R.id.action_cambiarMailLocalFragment_to_verificarNuevoMailLocalFragment)
            }
        }
    }

}