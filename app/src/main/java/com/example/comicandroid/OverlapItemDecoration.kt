package com.example.comicandroid

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class OverlapItemDecoration(private val overlapWidth: Int, private val paddingStart: Int, private var isNewRelease: Boolean = true) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = paddingStart
        if(isNewRelease) {
            if(parent.getChildAdapterPosition(view) == parent.adapter?.itemCount!! - 1) {
                outRect.right = overlapWidth
            }
        } else if(parent.getChildAdapterPosition(view) != 0) {
            outRect.right = overlapWidth
        }
    }
}