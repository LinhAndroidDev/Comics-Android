package com.example.comicandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.comicandroid.R
import com.example.comicandroid.screenWidth

class AdvertiseAdapter : Adapter<AdvertiseAdapter.MyViewHolder>() {
    private val advertises = listOf(R.drawable.advertise1, R.drawable.advertise2)

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAdvertiser: ImageView = itemView.findViewById(R.id.imgAdvertise)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_advertise, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = advertises.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val layoutParams = holder.itemView.layoutParams
        layoutParams.width = (screenWidth * 0.8).toInt()
        holder.itemView.layoutParams = layoutParams

        holder.imgAdvertiser.setImageResource(advertises[position])
    }
}