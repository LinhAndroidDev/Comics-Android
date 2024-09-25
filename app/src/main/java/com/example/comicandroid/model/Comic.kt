package com.example.comicandroid.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comic(
    val nameComics: String,
    val nameHeader: String,
    val sizePage: Int,
    val coverPhoto: Int
) : Parcelable