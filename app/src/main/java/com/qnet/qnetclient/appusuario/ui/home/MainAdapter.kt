package com.ian.bottomnavigation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_home2.view.*
import kotlinx.android.synthetic.main.row.view.*
import kotlinx.android.synthetic.main.fragment_home2.view.descripcion as descripcion1

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
            view.findNavController().navigate(R.id.home_action)
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



        /*var data:List<Model>

        init {
            this.data=data
        }*/
        // ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

       /* var title:TextView
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

    }*/


    /*override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.title.text = data[position].title
            holder.descripcion.text = data[position].descripcion
            holder.num.text = data[position].num
            holder.dist.text = data[position].dist
            Glide.with(context).load(data[position].image).into(holder.image)


            holder.itemView.setOnClickListener {view->
                view.findNavController().navigate(R.id.home_action)
            }


        }*/

}