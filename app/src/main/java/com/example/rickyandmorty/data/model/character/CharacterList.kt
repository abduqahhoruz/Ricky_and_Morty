package com.example.rickyandmorty.data.model.character

import com.example.rickyandmorty.data.model.Info

data class CharacterList(
    val info: Info,
    val results: List<CharacterModel>
)