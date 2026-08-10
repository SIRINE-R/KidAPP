package com.example.kidapp.presentation.screens.kidactivity

import com.example.kidapp.data.remote.dto.SearchTextsReq
import com.example.kidapp.data.remote.dto.VideoResponse

sealed interface KidActivityUIState {
    object Idle : KidActivityUIState
    object Loading : KidActivityUIState
    data class Success(val watchedVideos: List<VideoResponse>, val searchTexts: List<SearchTextsReq>) : KidActivityUIState
    data class Error(val message: String) : KidActivityUIState
}
