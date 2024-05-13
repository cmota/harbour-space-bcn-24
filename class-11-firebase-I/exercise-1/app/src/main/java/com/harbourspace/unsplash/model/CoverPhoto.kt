package com.harbourspace.unsplash.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.TypeConverters
import com.harbourspace.unsplash.model.converters.UrlsConverter
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity
@Parcelize
@Serializable
data class CoverPhoto(
    @field:TypeConverters(UrlsConverter::class)
    val urls: Urls
) : Parcelable