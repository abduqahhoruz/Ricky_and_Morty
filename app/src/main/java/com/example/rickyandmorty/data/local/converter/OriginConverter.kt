package com.example.rickyandmorty.data.local.converter

import androidx.room.TypeConverter
import com.example.rickyandmorty.data.model.character.Origin

class OriginConverter {
    @TypeConverter
    fun fromString(location: String): Origin {
        val list = location.split(",")
        return Origin(list[0], list[1])
    }

    @TypeConverter
    fun toString(origin: Origin): String {
        val name = origin.name
        val url = origin.url
        return "$name,$url"
    }
}