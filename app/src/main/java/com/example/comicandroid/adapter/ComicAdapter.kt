package com.example.comicandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.comicandroid.R
import com.example.comicandroid.model.Comic

class ComicAdapter : Adapter<ComicAdapter.MyViewHolder>() {
    var comics = listOf<Comic>()

    var onClickItem: ((Comic) -> Unit)? = null

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageComic: ImageView = itemView.findViewById(R.id.imgComic)
        val nameComic: TextView = itemView.findViewById(R.id.nameComic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ComicAdapter.MyViewHolder, position: Int) {
        val comic = comics[position]
        holder.imageComic.setImageResource(comic.coverPhoto)
        holder.nameComic.text = comic.nameComics

        holder.itemView.setOnClickListener {
            onClickItem?.invoke(comic)
        }
    }

    override fun getItemCount(): Int = comics.size

}