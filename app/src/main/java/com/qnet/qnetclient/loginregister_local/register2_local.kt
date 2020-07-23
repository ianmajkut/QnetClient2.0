package com.qnet.qnetclient.loginregister_local

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_login_register.buttonNext
import kotlinx.android.synthetic.main.fragment_register2.back_icon
import kotlinx.android.synthetic.main.fragment_register2_local.*
import kotlinx.android.synthetic.main.fragment_register_local.*


class register2_local : Fragment() {

    private lateinit var viewModel: FirestoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register2_local, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener{
            findNavController().navigate(R.id.verification_action_local)
        }
        back_icon.setOnClickListener{
            findNavController().navigate(R.id.back_action_local)
        }

    }
    private fun loadData(){
        val nombre = edtxt_nombreLocal.toString()
        val ubicacion= edtxt_ubicacion.toString()
        val horario = edtxt_horario.toString()
        val tipo = edtxt_tipo.toString()
        val informacion = edtxt_informacion.toString()

        if(nombre.isNotEmpty()&&ubicacion.isNotEmpty()&&horario.isNotEmpty()&&tipo.isNotEmpty()&&informacion.isNotEmpty()){
            viewModel.loadLocal(nombre,ubicacion, horario, tipo, informacion).observeForever{
                if(it){
                    findNavController().navigate(R.id.verification_action_local)
                    Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(activity, "Error falta algun campo", Toast.LENGTH_SHORT).show()
        }

    }

}
