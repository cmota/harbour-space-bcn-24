package com.harbourspace.unsplash.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LinksX(
    val html: String?,
    val likes: String?,
    val photos: String?,
    val portfolio: String?,
    val self: String?
) : Parcelable