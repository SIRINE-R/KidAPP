package com.example.kidapp.presentation.screens.search

import com.example.kidapp.data.remote.dto.VideoResponse
import com.example.kidapp.domain.model.Video

sealed interface SerachScreenUIState {
    object Idle : SerachScreenUIState
    object Loading : SerachScreenUIState
    data class Success(val videos: List<Video>) : SerachScreenUIState
    data class Error(val message: String) : SerachScreenUIState
}