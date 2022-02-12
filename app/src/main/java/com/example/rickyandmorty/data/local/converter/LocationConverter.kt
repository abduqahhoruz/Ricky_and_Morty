package com.example.rickyandmorty.data.local.converter

import androidx.room.TypeConverter
import com.example.rickyandmorty.data.model.character.Location

class LocationConverter {
    @TypeConverter
    fun fromString(location: String): Location {
        val list = location.split(",")
        return Location(list[0], list[1])
    }

    @TypeConverter
    fun toString(location: Location): String {
        val name = location.name
        val url = location.url
        return "$name,$url"
    }
}