package com.qnet.qnetclient.applocal.ui.fila

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ian.bottomnavigation.ui.home.Model
import com.qnet.qnetclient.R
import com.qnet.qnetclient.appusuario.ui.fila.AdapterFila
import com.qnet.qnetclient.data.classes.Usuario
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.android.synthetic.main.row.view.tittle
import kotlinx.android.synthetic.main.row_filalocal.view.*

class AdapterLocal (private val context: Context): RecyclerView.Adapter<AdapterLocal.LocalViewHolder>() {

    private var dataList = mutableListOf<Usuario>()

    fun setListData(data:MutableList<Usuario>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterLocal.LocalViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.row_filalocal,parent,false)
        return LocalViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0){
            dataList.size
        }else{
            0
        }
    }

    override fun onBindViewHolder(holder: AdapterLocal.LocalViewHolder, position: Int) {
        val local: Usuario = dataList[position]
        holder.bindView(local)
        holder.itemView.setOnClickListener {view->
            view.findNavController().navigate(R.id.action_filaLocalFragment_to_lectorQr_Activity)
        }
    }

    inner class LocalViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun  bindView(user: Usuario) {
            itemView.tittle.text= user.name
            itemView.position.text = user.position.toString()
        }
    }
}