package com.ian.bottomnavigation.ui.fila

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.ian.bottomnavigation.ui.home.MainAdapter

import com.qnet.qnetclient.R
import com.qnet.qnetclient.appusuario.AppUser
import com.qnet.qnetclient.appusuario.ui.fila.AdapterFila
import com.qnet.qnetclient.local_o_usuario
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_fila.*
import kotlinx.android.synthetic.main.fragment_fila.shimmer_view_container
import kotlinx.android.synthetic.main.fragment_home.*

class FilaFragment : Fragment() {

    lateinit var handler: Handler
    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast
    private val viewModel by lazy { FirestoreViewModel() }
    private lateinit var adapter: AdapterFila

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout= inflater.inflate(R.layout.fragment_fila, container, false)

        val recycler = layout.findViewById<RecyclerView>(R.id.recyclerViewMisColas)
        val txt_MisColas = layout.findViewById<TextView>(R.id.text_notifications)
        val shimmer_view_container= layout.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)


        recycler.layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
        adapter = AdapterFila(requireActivity().applicationContext)
        recycler.adapter = adapter

        shimmer_view_container.startShimmer()

        observerData()
//        Toast.makeText(activity, "${adapter.itemCount}", Toast.LENGTH_SHORT).show()

        return layout

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                val intent = Intent(requireContext(), AppUser::class.java)
                startActivity(intent)
                backToast.cancel()
                requireActivity().moveTaskToBack(true)
                requireActivity().finish()
            } else {
                backToast = Toast.makeText(
                    requireContext(),
                    "Presione nuevamente \"Atrás\" para salir",
                    Toast.LENGTH_SHORT
                )
                backToast.show()
            }
            backPressedTime = System.currentTimeMillis()

        }
    }

    fun observerData() {
        viewModel.fetchMisColas().observe(viewLifecycleOwner, Observer {

            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            if (it.size==0){
                text_notifications.visibility = View.VISIBLE
            }else{
                text_notifications.visibility = View.GONE
            }


        })

    }
}