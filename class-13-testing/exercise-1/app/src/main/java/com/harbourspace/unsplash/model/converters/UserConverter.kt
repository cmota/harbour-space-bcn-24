package com.harbourspace.unsplash.model.converters

import androidx.room.TypeConverter
import com.harbourspace.unsplash.model.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserConverter {

    @TypeConverter
    fun fromUserType(value: User?): String = Json.encodeToString(value)

    @TypeConverter
    fun toUserType(value: String): User? = Json.decodeFromString(value)
}