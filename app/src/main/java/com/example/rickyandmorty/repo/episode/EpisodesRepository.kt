package com.example.rickyandmorty.repo.episode

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.rickyandmorty.common.Constants
import com.example.rickyandmorty.data.local.AppDataBase
import com.example.rickyandmorty.data.local.LocalInjector
import com.example.rickyandmorty.data.model.episode.EpisodeModel
import com.example.rickyandmorty.data.remote.ApiService
import com.example.rickyandmorty.data.remote.RemoteInjector

class EpisodesRepository(
    private val apiService: ApiService = RemoteInjector.injectApiService()!!,
    private val appDataBase: AppDataBase? = LocalInjector.injectDb()
) {

    companion object {
        fun getInstance() = EpisodesRepository()
    }

    @ExperimentalPagingApi
    fun getEpisodesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<EpisodeModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { EpisodesPagingSource(apiService) }
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = Constants.DEFAULT_EPISODES_PAGE_SIZE,
            prefetchDistance = 2,
            enablePlaceholders = true
        )
    }

    @ExperimentalPagingApi
    fun getEpisodesFlowDb(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<EpisodeModel>> {
        if (appDataBase == null) throw IllegalStateException("Database is not initialized")

        val pagingSourceFactory = { appDataBase.episodeDao().getAllEpisode() }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = EpisodeMediator(apiService, appDataBase)
        ).flow
    }

    fun getEpisodeFlowDb(id: Long): Flow<EpisodeModel> {
        if (appDataBase == null) throw IllegalStateException("Database is not initialized")

        return flow {
            var episode: EpisodeModel? = appDataBase.episodeDao().getEpisode(id)
            if (episode == null) {
                val res = apiService.getEpisode(id)
                if (res.isSuccessful && res.body() != null) {
                    episode = res.body()
                    appDataBase.episodeDao().insert(episode!!)
                }
            }
            emit(episode!!)
        }
    }

    fun getCharactersInEpisode(ids: List<Long>): Flow<List<EpisodeModel>> {
        if (appDataBase == null) throw IllegalStateException("Database is not initialized")

        return flow {
            val episodeList = mutableListOf<EpisodeModel>()
            ids.forEach { id ->
                var episode: EpisodeModel? = appDataBase.episodeDao().getEpisode(id)
                if (episode == null) {
                    val res = apiService.getEpisode(id)
                    if (res.isSuccessful && res.body() != null) {
                        episode = res.body()
                        appDataBase.episodeDao().insert(episode!!)
                    }
                }
                episodeList.add(episode!!)
            }
            emit(episodeList)
        }
    }
}

