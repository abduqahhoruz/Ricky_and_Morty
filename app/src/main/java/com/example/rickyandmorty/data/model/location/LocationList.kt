package com.example.rickyandmorty.data.model.location

import com.example.rickyandmorty.data.model.Info

data class LocationList(
    val info: Info,
    val results: List<LocationModel>
)