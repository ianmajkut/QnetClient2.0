package com.ian.bottomnavigation.ui.home


import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.qnet.qnetclient.R
import com.qnet.qnetclient.appusuario.AppUser
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private val viewModel by lazy { FirestoreViewModel()}
    private lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout=inflater.inflate(R.layout.fragment_home, container, false)
        val shimmer_view_container= layout.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)

        val recycler = layout.findViewById<RecyclerView>(R.id.recyclerview)

        shimmer_view_container.startShimmer()

        observerData()

       /* shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility=View.GONE*/

        adapter = MainAdapter(requireActivity().applicationContext)
        recycler.layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
        recycler.adapter = adapter

        setHasOptionsMenu(true)
        return layout

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.searchbar, menu)
        val searchView = SearchView((context as AppUser).supportActionBar?.themedContext ?: context)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.findViewById<TextView>(R.id.search_src_text).setTextColor(Color.WHITE)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        searchView.setOnClickListener { }
    }

    fun observerData() {

        viewModel.fetchLocalData().observe(viewLifecycleOwner, Observer {
            //swipeRefreshLayout.isRefreshing=false
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()

        })
    }

}