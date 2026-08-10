package com.example.kidapp.presentation.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.R
import com.example.kidapp.domain.model.Video
import com.example.kidapp.domain.model.use_case.AddSearchedTexts
import com.example.kidapp.domain.model.use_case.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase,
    private val addSearchedTexts: AddSearchedTexts
) : ViewModel() {
    private val _uiState = MutableStateFlow<SerachScreenUIState>(SerachScreenUIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val drawableToKeyMap = mapOf(
        R.drawable.rabbit to "rabbit",
        R.drawable.bob to "bob",
        R.drawable.bird to "bird",
        R.drawable.dorimen to "dorimen",
        R.drawable.jerry to "jerry",
        R.drawable.mikymouse to "mikymouse",
        R.drawable.panda to "panda"
    )


    private val keyToDrawableMap = drawableToKeyMap.entries.associate { it.value to it.key }

    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    private val _video = MutableStateFlow<List<Video>>(emptyList())
    val video = _video.asStateFlow()


    fun onQueryChanged(query: String) {
        _queryText.value = query
    }

    fun videoMetaData(video: List<Video>) {
        _video.value = video
    }

    fun getDrawableRes(iconKey: String?): Int {
        return keyToDrawableMap[iconKey] ?: R.drawable.panda
    }

    fun performSearch(id: Int, query: String, searchedAt: LocalDateTime) {
        viewModelScope.launch {
            _uiState.value = SerachScreenUIState.Loading

            addSearchedTexts(id, query, searchedAt)
                .onFailure { Log.e("SearchHistory", "Failed to save history: ${it.message}") }

            getVideosUseCase(query = query)
                .onSuccess { responseList ->
                    val mapped = responseList.map {
                        Video(
                            video_id = it.video_id,
                            title = it.title,
                            description = it.description,
                            thumbnail_url = it.thumbnail_url
                        )
                    }
                    videoMetaData(mapped)
                    _uiState.value = SerachScreenUIState.Success(mapped)
                }
                .onFailure {
                    _uiState.value = SerachScreenUIState.Error(it.message ?: "Unknown error")
                }
        }
    }

}

