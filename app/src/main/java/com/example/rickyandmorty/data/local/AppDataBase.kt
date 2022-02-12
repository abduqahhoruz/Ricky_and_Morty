package com.example.rickyandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickyandmorty.data.local.converter.LocationConverter
import com.example.rickyandmorty.data.local.converter.OriginConverter
import com.example.rickyandmorty.data.local.converter.StringListConverter
import com.example.rickyandmorty.data.local.dao.*
import com.example.rickyandmorty.data.model.character.CharacterKeys
import com.example.rickyandmorty.data.model.character.CharacterModel
import com.example.rickyandmorty.data.model.episode.EpisodeKeys
import com.example.rickyandmorty.data.model.episode.EpisodeModel
import com.example.rickyandmorty.data.model.location.LocationKeys
import com.example.rickyandmorty.data.model.location.LocationModel

@Database(
    entities = [CharacterModel::class, EpisodeModel::class, LocationModel::class,
        CharacterKeys::class, EpisodeKeys::class, LocationKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [StringListConverter::class, OriginConverter::class, LocationConverter::class])
abstract class AppDataBase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun locationDao(): LocationDao
    abstract fun characterKeysDao(): CharacterKeysDao
    abstract fun episodeKeysDao(): EpisodeKeysDao
    abstract fun locationKeysDao(): LocationKeysDao
}