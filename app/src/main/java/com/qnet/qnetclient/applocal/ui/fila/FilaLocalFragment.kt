package com.qnet.qnetclient.applocal.ui.fila

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qnet.qnetclient.R
import com.qnet.qnetclient.appusuario.ui.fila.AdapterFila
import com.qnet.qnetclient.viewModel.FirestoreViewModel


class FilaLocalFragment : Fragment() {

    private val viewModel by lazy { FirestoreViewModel() }

    private lateinit var adapter: AdapterLocal
    private lateinit var num:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_fila_local, container, false)
        num = layout.findViewById(R.id.cantidad_clientes)
        val recycler = layout.findViewById<RecyclerView>(R.id.recyclerview)

        recycler.layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
        adapter = AdapterLocal(requireActivity().applicationContext)
        recycler.adapter = adapter
        observerData()

        return layout

    }
    private fun observerData(){
        viewModel.fetchUsuarios().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            num.text = it.size.toString()
        })
    }

}