package com.ian.bottomnavigation.ui.fila

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ian.bottomnavigation.ui.home.MainAdapter

import com.qnet.qnetclient.R
import com.qnet.qnetclient.appusuario.ui.fila.AdapterFila
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_fila.*

class FilaFragment : Fragment() {

    private val viewModel by lazy { FirestoreViewModel() }

    private lateinit var adapter: AdapterFila

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout= inflater.inflate(R.layout.fragment_fila, container, false)

        val recycler = layout.findViewById<RecyclerView>(R.id.recyclerViewMisColas)
        val txt_MisColas = layout.findViewById<TextView>(R.id.text_notifications)


        recycler.layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
        adapter = AdapterFila(requireActivity().applicationContext)
        recycler.adapter = adapter
        observerData()
        Toast.makeText(activity, "${adapter.itemCount}", Toast.LENGTH_SHORT).show()
        if(adapter.itemCount != 0){
            txt_MisColas.visibility = View.GONE
        }

        return layout

    }
    fun observerData(){
        viewModel.fetchMisColas().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })
    }
}