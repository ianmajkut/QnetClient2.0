package com.ian.bottomnavigation.ui.fila

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_fila.*

class FilaFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val layout= inflater.inflate(R.layout.fragment_fila, container, false)

        return layout

    }

}