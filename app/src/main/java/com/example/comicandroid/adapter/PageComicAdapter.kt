package com.example.comicandroid.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comicandroid.R
import com.example.comicandroid.databinding.ItemPageComicBinding

class PageComicAdapter : RecyclerView.Adapter<PageComicAdapter.ComicViewHolder>() {
    var comics = arrayListOf<Bitmap>()
    var selectPosition = 0
    var onClickItem: ((Int) -> Unit)? = null

    inner class ComicViewHolder(private val v: ItemPageComicBinding) :
        RecyclerView.ViewHolder(v.root) {
        @SuppressLint("SetTextI18n")
        fun bind(comic: Bitmap, isSelected: Boolean = false, index: Int) {
            Glide.with(v.root).load(comic).into(v.imgComic)
            v.viewCover.isSelected = isSelected
            if(isSelected) {
                v.tvIndex.setTextColor(ContextCompat.getColor(v.root.context, R.color.green))
                v.tvIndex.typeface = Typeface.DEFAULT_BOLD
            } else {
                v.tvIndex.setTextColor(ContextCompat.getColor(v.root.context, R.color.white))
                v.tvIndex.typeface = Typeface.DEFAULT
            }
            v.tvIndex.text = "${index + 1}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageComicAdapter.ComicViewHolder {
        val v = ItemPageComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicViewHolder(v)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: PageComicAdapter.ComicViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val isSelected = selectPosition == position
        holder.bind(comics[position], isSelected, position)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(position)
            selectPosition = position
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setIndexSelected(index: Int) {
        selectPosition = index
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = comics.size
}