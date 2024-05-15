package com.harbourspace.unsplash.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity
@Parcelize
@Serializable
data class ProfileImage(
    val large: String?,
    val medium: String?,
    val small: String?
) : Parcelable