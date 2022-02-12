package com.example.rickyandmorty.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.rickyandmorty.data.model.character.CharacterList
import com.example.rickyandmorty.data.model.character.CharacterModel
import com.example.rickyandmorty.data.model.episode.EpisodeList
import com.example.rickyandmorty.data.model.episode.EpisodeModel
import com.example.rickyandmorty.data.model.location.LocationList
import com.example.rickyandmorty.data.model.location.LocationModel

interface ApiService {

    @GET("character")
    suspend fun getAllCharactersInPage(@Query("page") page:Int): Response<CharacterList>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Long): Response<CharacterModel>

    @GET("location")
    suspend fun getAllLocationsInPage(@Query("page") page:Int): Response<LocationList>

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: Long): Response<LocationModel>

    @GET("episode")
    suspend fun getAllEpisodesInPage(@Query("page") page:Int): Response<EpisodeList>

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Long): Response<EpisodeModel>

}