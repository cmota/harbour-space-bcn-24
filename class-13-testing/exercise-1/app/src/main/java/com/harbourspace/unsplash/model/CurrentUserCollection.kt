package com.harbourspace.unsplash.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Entity
@Parcelize
@Serializable
data class CurrentUserCollection(
    val cover_photo: @Contextual @RawValue Any?,
    val id: Int,
    val last_collected_at: String?,
    val published_at: String?,
    val title: String?,
    val updated_at: String?,
    val user: @Contextual @RawValue Any?
) : Parcelable