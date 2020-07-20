package com.qnet.qnetclient.applocal.ui.fila

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qnet.qnetclient.R
import com.qnet.qnetclient.appusuario.ui.fila.AdapterFila
import com.qnet.qnetclient.viewModel.FirestoreViewModel


class FilaLocalFragment : Fragment() {

    private val viewModel by lazy { FirestoreViewModel() }

    private lateinit var adapter: AdapterLocal

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_fila_local, container, false)

        val recycler = layout.findViewById<RecyclerView>(R.id.recyclerViewMisColas)

        recycler.layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
        adapter = AdapterLocal(requireActivity().applicationContext)
        recycler.adapter = adapter
        observerData()

        return layout

    }
    private fun observerData(){
        viewModel.fetchMisColas().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })
    }

}