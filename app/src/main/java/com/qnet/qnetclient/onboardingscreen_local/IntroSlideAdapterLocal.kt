package com.qnet.qnetclient.onboardingscreen_local

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qnet.qnetclient.R


class IntroSlideAdapterLocal (private  val introSlideLocal:List<IntroSlideLocal>):RecyclerView.Adapter<IntroSlideAdapterLocal.IntroSlideViewHolderLocal>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolderLocal {
        return IntroSlideViewHolderLocal(LayoutInflater.from(parent.context).inflate(R.layout.slide_item_container_local,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return introSlideLocal.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolderLocal, position: Int) {
        holder.bind(introSlideLocal[position])
    }

    inner class IntroSlideViewHolderLocal(view: View): RecyclerView.ViewHolder(view){
        private val textTitle=view.findViewById<TextView>(R.id.textTittle)
        private val textDescription=view.findViewById<TextView>(R.id.textDescription)
        private val imageIcon=view.findViewById<ImageView>(R.id.imageSlideIcon)

        fun bind(introSlideLocal: IntroSlideLocal){
            textTitle.text=introSlideLocal.title
            textDescription.text= introSlideLocal.description
            imageIcon.setImageResource(introSlideLocal.icon)
        }

    }

}