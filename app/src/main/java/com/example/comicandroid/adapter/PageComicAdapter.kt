package com.example.comicandroid.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comicandroid.databinding.ItemPageComicBinding

class PageComicAdapter : RecyclerView.Adapter<PageComicAdapter.ComicViewHolder>() {
    var comics = arrayListOf<Bitmap>()
    private var selectPosition = 0

    inner class ComicViewHolder(private val v: ItemPageComicBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(comic: Bitmap, isSelected: Boolean = false) {
            Glide.with(v.root).load(comic).into(v.imgComic)
            v.viewCover.setBackgroundColor(
                if (isSelected) Color.parseColor("#2647FD") else Color.TRANSPARENT
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageComicAdapter.ComicViewHolder {
        val v = ItemPageComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicViewHolder(v)
    }

    override fun onBindViewHolder(holder: PageComicAdapter.ComicViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val isSelected = selectPosition == position
        holder.bind(comics[position], isSelected)
        holder.itemView.setOnClickListener {
            selectPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = comics.size
}