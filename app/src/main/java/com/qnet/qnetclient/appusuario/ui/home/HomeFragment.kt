package com.ian.bottomnavigation.ui.home


import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qnet.qnetclient.R
import com.qnet.qnetclient.appusuario.AppUser


class HomeFragment : Fragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout=inflater.inflate(R.layout.fragment_home, container, false)

        val item = mutableListOf<Model>()


        item.add(Model("Jumbo","Supermercado","0","10km" ,R.drawable.jumbo))
        item.add(Model("Dia","Supermercado","100", "800km",R.drawable.dia))
        item.add(Model("Carrefour","Supermercado","2","50m" ,R.drawable.carrefour))
        item.add(Model("Easy","De todo ","30","100km" ,R.drawable.easy))
        item.add(Model("Sodimac","De todo","40", "16km",R.drawable.sodimac))
        item.add(Model("Coto","Supermercado","60","300m" ,R.drawable.descarga))



        val recycler = layout.findViewById<RecyclerView>(R.id.recyclerview)




        val adapter = MainAdapter(item as ArrayList<Model>, requireActivity().applicationContext)
        recycler.layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
        recycler.adapter = adapter


        setHasOptionsMenu(true)
        return layout


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater?.inflate(R.menu.searchbar, menu)
        val searchView = SearchView((context as AppUser).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView.setOnClickListener {view ->  }
    }



    }

