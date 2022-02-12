package com.example.rickyandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickyandmorty.data.model.character.CharacterKeys

@Dao
interface CharacterKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterKey: List<CharacterKeys>)

    @Query("select * from character_keys where repoId = :id")
    suspend fun remoteKeysCharacterId(id: Long): CharacterKeys?

    @Query("delete from character_keys")
    suspend fun clearCharacterKeys()
}