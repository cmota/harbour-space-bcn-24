package com.harbourspace.unsplash.model.converters

import androidx.room.TypeConverter
import com.harbourspace.unsplash.model.ProfileImage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProfileImageConverter {

    @TypeConverter
    fun fromProfileImageType(value: ProfileImage?): String = Json.encodeToString(value)

    @TypeConverter
    fun toProfileImageType(value: String): ProfileImage? = Json.decodeFromString(value)
}