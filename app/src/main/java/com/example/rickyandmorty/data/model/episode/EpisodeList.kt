package com.example.rickyandmorty.data.model.episode

import com.example.rickyandmorty.data.model.Info

data class EpisodeList(
    val info: Info,
    val results: List<EpisodeModel>
)