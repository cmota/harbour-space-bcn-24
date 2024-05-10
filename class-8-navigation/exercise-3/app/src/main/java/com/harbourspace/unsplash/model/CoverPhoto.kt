package com.harbourspace.unsplash.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverPhoto(
    val urls: Urls
) : Parcelable