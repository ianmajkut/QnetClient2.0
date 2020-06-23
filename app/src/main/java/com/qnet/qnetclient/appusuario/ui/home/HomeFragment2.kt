package com.ian.bottomnavigation.ui.home

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.qnet.qnetclient.R

class HomeFragment2 : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout= inflater.inflate(R.layout.fragment_home2, container, false)

        val bt:Button=layout.findViewById(R.id.btn_sum)

        bt.setOnClickListener{
            alerta()

        }
        return layout
    }

    fun alerta(){

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Alerta")
        alertDialog.setMessage("Está a punto de sumarse a la fila online. ¿Está seguro?")

        alertDialog.setPositiveButton("Si") { _, _ ->

            findNavController().navigate(R.id.fragment2_to_fila)
            

        }

        alertDialog.setNegativeButton("No") { _, _ ->

            Toast.makeText(context, "No", Toast.LENGTH_LONG).show()

        }
        alertDialog.show()



    }




}