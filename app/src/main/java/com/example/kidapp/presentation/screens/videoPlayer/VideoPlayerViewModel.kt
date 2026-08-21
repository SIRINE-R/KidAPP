package com.example.kidapp.presentation.screens.videoPlayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.domain.model.Video
import com.example.kidapp.domain.model.use_case.AddWatchedVideoUseCase
import com.example.kidapp.domain.model.use_case.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase,
    private val addVideoUseCase: AddWatchedVideoUseCase
) : ViewModel() {

    private val _relatedVideos = MutableStateFlow<List<Video>>(emptyList())
    val relatedVideos = _relatedVideos.asStateFlow()

    private val _isFullScreen = MutableStateFlow(false)
    val isFullScreen = _isFullScreen.asStateFlow()

    init {
        getRelatedVideos("kids")
    }

    fun toggleFullScreen(enabled: Boolean) {
        _isFullScreen.value = enabled
    }
    fun getRelatedVideos(query: String) {
        viewModelScope.launch {
            getVideosUseCase(query).onSuccess { responseList ->
                _relatedVideos.value = responseList.map {
                    Video(
                        video_id = it.video_id,
                        title = it.title,
                        description = it.description,
                        thumbnail_url = it.thumbnail_url
                    )
                }
            }
        }
    }

    fun addWatchedVideo(
        childId: Int,
        videoId: String,
        watchedAt: LocalDateTime
    ) {
        viewModelScope.launch {
            addVideoUseCase(
                childId = childId,
                videoId = videoId,
                watchedAt = watchedAt
            ).onSuccess { response ->
                android.util.Log.d("VideoPlayerViewModel", "Successfully added watched video: $response")
            }.onFailure { error ->
                android.util.Log.e("VideoPlayerViewModel", "Failed to add watched video: ${error.message}", error)
            }
        }
    }
}
