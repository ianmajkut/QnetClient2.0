package com.qnet.qnetclient.appusuario.ui.fila

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ian.bottomnavigation.ui.home.HomeFragmentDirections
import com.ian.bottomnavigation.ui.home.MainAdapter
import com.ian.bottomnavigation.ui.home.Model
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.row.view.*

class AdapterFila (private val context: Context): RecyclerView.Adapter<AdapterFila.FilaViewHolder>() {
    private var dataList = mutableListOf<Model>()

    fun setListData(data:MutableList<Model>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilaViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.row_filausuario,parent,false)
        return FilaViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0 ){
            dataList.size
        }else{
            0
        }
    }

    override fun onBindViewHolder(holder: FilaViewHolder, position: Int) {
        val local: Model = dataList[position]
        holder.bindView(local)
        holder.itemView.setOnClickListener {view->


            view.findNavController().navigate(R.id.fila_to_qr)
        }
    }


    inner class FilaViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun  bindView(local: Model) {
            itemView.tittle.text= local.title
            itemView.descripcion.text = local.descripcion
            itemView.Fila.text = local.posicion
            itemView.Dist.text = local.dist
        }
    }




}