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
        holder.image.setImageResource(data[position].image)


        holder.itemView.setOnClickListener {view->

            view.findNavController().navigate(R.id.home_action)

            /*
            if(position==0){

                Toast.makeText(context,"Click 1",Toast.LENGTH_SHORT).show()

            }
            if(position==1){

                Toast.makeText(context,"Click 2",Toast.LENGTH_SHORT).show()

            }
            if(position==2){

                Toast.makeText(context,"Click 3",Toast.LENGTH_SHORT).show()

            }
            if(position==3){

                Toast.makeText(context,"Click 4",Toast.LENGTH_SHORT).show()

            }
            if(position==4){

                Toast.makeText(context,"Click 5",Toast.LENGTH_SHORT).show()

            }
            if(position==5){

                Toast.makeText(context,"Click 6",Toast.LENGTH_SHORT).show()

            }
            if(position==6){

                Toast.makeText(context,"Click 7",Toast.LENGTH_SHORT).show()

            }
            */

            /*
            val model=data.get(position)

            var gTitle:String?=model.title
            var gDesc:String?=model.descripcion
            var gImageView:Int=model.image

            val intent=Intent(context,HomeFragment2::class.java)

            intent.putExtra("iTitle",gTitle)
            intent.putExtra("iDesc",gDesc)
            intent.putExtra("iImageView",gImageView)

            context.startActivity(intent)
            */

        }


    }


}