package com.ian.bottomnavigation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.row.view.*

class MainAdapter(private val context: Context): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var dataList = mutableListOf<Model>()

    fun setListData(data:MutableList<Model>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.row,parent,false)
        return MainViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0 ){
            dataList.size
        }else{
            0
        }
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val local:Model = dataList[position]
        holder.bindView(local)
        holder.itemView.setOnClickListener {view->

            val action = HomeFragmentDirections.homeAction(local)
            view.findNavController().navigate(action)
        }
    }


    inner class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun  bindView(local:Model) {
            Glide.with(context).load(local.image).into(itemView.Image)
            itemView.tittle.text= local.title
            itemView.descripcion.text = local.descripcion
            itemView.Fila.text = local.num
            itemView.Dist.text = local.dist

        }
    }

}