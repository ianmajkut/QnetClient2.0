package com.qnet.qnetclient.applocal.ui.info_local

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_cambiar_horario_local.*
import kotlinx.android.synthetic.main.fragment_cambiar_horario_local.back_icon
import kotlinx.android.synthetic.main.fragment_cambiar_horario_local.buttonNext


class CambiarHorarioLocalFragment : Fragment() {

    private lateinit var viewModel : FirestoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambiar_horario_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_icon.setOnClickListener {

            findNavController().navigate(R.id.action_cambiarHorarioLocalFragment_to_infoLocal_Fragment)

        }
        buttonNext.setOnClickListener {
            cambiar()
        }
    }
    private fun cambiar(){

        val data = edtxt_horarioDelLocal.text.toString()
        if (data.isNotEmpty()) {
            viewModel = FirestoreViewModel()
            viewModel.changeData("horario", data).observeForever {
                if (it) {
                    findNavController().navigate(R.id.action_cambiarHorarioLocalFragment_to_infoLocal_Fragment)
                }
            }
        }
    }

    
}