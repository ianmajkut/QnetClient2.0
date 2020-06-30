package com.ian.bottomnavigation.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qnet.qnetclient.R

class MainAdapter(data:ArrayList<Model>,var context:Context):RecyclerView.Adapter<MainAdapter.ViewHolder> (){

var data:List<Model>

    init {
        this.data=data
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        var title:TextView
        var descripcion:TextView
        var image:ImageView
        var num:TextView
        var dist:TextView

        init {
            title=itemView.findViewById(R.id.tittle)
            descripcion=itemView.findViewById(R.id.descripcion)
            image=itemView.findViewById(R.id.Image)
            num=itemView.findViewById(R.id.Fila)
            dist=itemView.findViewById(R.id.Dist)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val layout=LayoutInflater.from(context).inflate(R.layout.row,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = data[position].title
        holder.descripcion.text = data[position].descripcion
        holder.num.text = data[position].num
        holder.dist.text = data[position].dist
        Glide.with(context).load(data[position].image).into(holder.image)


        holder.itemView.setOnClickListener {view->

            view.findNavController().navigate(R.id.home_action)


        }


    }


}