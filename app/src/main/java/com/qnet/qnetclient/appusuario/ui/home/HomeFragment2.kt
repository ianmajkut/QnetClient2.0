package com.ian.bottomnavigation.ui.home

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.row.view.*

class HomeFragment2 : Fragment() {
    private lateinit var viewModel: FirestoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val local = HomeFragment2Args.fromBundle(requireArguments()).Local
        // Inflate the layout for this fragment
        val layout= inflater.inflate(R.layout.fragment_home2, container, false)

        val bt:Button=layout.findViewById(R.id.btn_sum)
        val image:ImageView = layout.findViewById(R.id.image)
        val titulo:TextView = layout.findViewById(R.id.titulo)
        val descripcion:TextView = layout.findViewById(R.id.descripcion)
        val distancia:TextView = layout.findViewById(R.id.distancia)
        val fila:TextView = layout.findViewById(R.id.fila)
        val ubicacion:TextView = layout.findViewById(R.id.ubicacion)
        val horario:TextView = layout.findViewById(R.id.horario)
        val telefono:TextView = layout.findViewById(R.id.telefono)
        val mini_Desc:TextView = layout.findViewById(R.id.mini_desc)

        viewModel = ViewModelProvider(this).get(FirestoreViewModel::class.java)

        Glide.with(requireContext()).load(local.image).into(image)
        titulo.text = local.title
        descripcion.text= local.descripcion
        fila.text= local.num
        distancia.text = local.dist
        ubicacion.text = local.direccion
        horario.text = local.horario
        telefono.text = local.telefono
        mini_Desc.text = local.informacion
        //bindearDatos(Local.Local,requireContext())*/

        val keyLocal = local.keyLocal

        bt.setOnClickListener{
            alerta(keyLocal,local.dist)
//            viewModel.enviarDatos(keyLocal)
        }
        return layout
    }

    fun alerta(keyLocal: String?, distancia:String?) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Alerta")
        alertDialog.setMessage("Está a punto de sumarse a la fila online. ¿Está seguro?")

        alertDialog.setPositiveButton("Si") { _, _ ->
            observer(keyLocal, distancia)
        }

        alertDialog.setNegativeButton("No") { _, _ ->
            Toast.makeText(context, "No", Toast.LENGTH_LONG).show()
        }
        alertDialog.show()
    }

    private fun observer(keyLocal: String?, distancia: String?) {
        viewModel.enviarDatos(keyLocal, distancia).observeForever {
            if (it) {
                findNavController().navigate(R.id.fragment2_to_fila)
            }
        }
    }
}