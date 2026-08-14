package com.example.kidapp.presentation.screens.kidscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.R
import com.example.kidapp.domain.model.Video
import com.example.kidapp.domain.model.use_case.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KidHomeScreenViewModel @Inject constructor(
    private val getListOfVideosUseCase: GetVideosUseCase
): ViewModel() {
    private val drawableToKeyMap = mapOf(
        R.drawable.rabbit to "rabbit",
        R.drawable.bob to "bob",
        R.drawable.bird to "bird",
        R.drawable.dorimen to "dorimen",
        R.drawable.jerry to "jerry",
        R.drawable.mikymouse to "mikymouse",
        R.drawable.panda to "panda"
    )
    private val _uiState = MutableStateFlow<KidHomeUIState>(KidHomeUIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos = _videos.asStateFlow()


    private val keyToDrawableMap = drawableToKeyMap.entries.associate { it.value to it.key }


    fun getDrawableRes(iconKey: String?): Int {
        return keyToDrawableMap[iconKey] ?: R.drawable.panda
    }

    fun getListOfVideos() {
        viewModelScope.launch {
            _uiState.value = KidHomeUIState.Loading
            val result = getListOfVideosUseCase(query = "baby")
            result.onSuccess { responses ->
                val videosList = responses.map {
                    Video(
                        video_id = it.video_id,
                        title = it.title,
                        description = it.description,
                        thumbnail_url = it.thumbnail_url
                    )
                }
                _videos.value = videosList
                _uiState.value = KidHomeUIState.Success(children = emptyList(), listVideos = videosList)
            }.onFailure {
                _uiState.value = KidHomeUIState.Error(it.message ?: "Failed to fetch page data")
            }
        }
    }
}
