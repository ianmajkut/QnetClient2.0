package com.qnet.qnetclient.loginregister_usuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_login_register.*


class login_register : Fragment() {


    private lateinit var viewModel: FirestoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_register, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FirestoreViewModel()
            buttonNew.setOnClickListener{
                findNavController().navigate(R.id.next_action)
            }
            buttonForget.setOnClickListener{
                findNavController().navigate(R.id.forget_action)
            }
            buttonNext.setOnClickListener{
                login()
            }

    }

    private fun login()
    {
        val name = edtxt_eMail.text.toString().trim()
        val password = edtxt_Password.text.toString().trim()

        if(name.isNotEmpty() && password.isNotEmpty()) {

           if(viewModel.singInUser(name,password)) {
               Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
               findNavController().navigate(R.id.menu_principal_action)

           }else {
               Toast.makeText(activity, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show()
           }

        }else {
            Toast.makeText(activity, "Error Campos Incompletos", Toast.LENGTH_SHORT).show()
        }


    }


}
