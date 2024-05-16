package com.harbourspace.unsplash.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.harbourspace.unsplash.model.converters.CoverPhotoConverter
import com.harbourspace.unsplash.model.converters.LinksConverter
import com.harbourspace.unsplash.model.converters.ListCurrentUserCollectionConverter
import com.harbourspace.unsplash.model.converters.UrlsConverter
import com.harbourspace.unsplash.model.converters.UserConverter
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity
@Parcelize
@Serializable
data class UnsplashItem(
    val blur_hash: String?,
    val color: String?,
    val created_at: String?,
    @field:TypeConverters(ListCurrentUserCollectionConverter::class)
    val current_user_collections: List<CurrentUserCollection>?,
    val description: String?,
    val height: Int?,
    @PrimaryKey
    val id: String,
    val liked_by_user: Boolean?,
    val likes: Int?,
    @field:TypeConverters(LinksConverter::class)
    val links: Links?,
    val updated_at: String?,
    @field:TypeConverters(UrlsConverter::class)
    val urls: Urls?,
    @field:TypeConverters(UserConverter::class)
    val user: User?,
    val width: Int?,
    @field:TypeConverters(CoverPhotoConverter::class)
    val cover_photo: CoverPhoto?
) : Parcelable