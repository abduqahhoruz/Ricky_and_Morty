package com.example.rickyandmorty.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.example.rickyandmorty.data.model.character.CharacterModel
import com.example.rickyandmorty.repo.charakter.CharactersRepository

@ExperimentalPagingApi
class CharactersViewModel(
    private val repo: CharactersRepository = CharactersRepository.getInstance()
) : ViewModel() {

    private lateinit var _charactersFlow: Flow<PagingData<CharacterModel>>
    val charactersFlow: Flow<PagingData<CharacterModel>>
        get() = _charactersFlow

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        _charactersFlow= try {
            repo.getCharactersFlowDb().cachedIn(viewModelScope)
        } catch (e: Exception) {
            flowOf(PagingData.empty())
        }
    }
}

