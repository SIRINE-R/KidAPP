package com.example.kidapp.presentation.screens.kidscreen

import com.example.kidapp.domain.model.Video

sealed interface KidHomeUIState{
    data class Success(val children: List<String>, val listVideos: List<Video>) : KidHomeUIState
    data class Error(val message: String) : KidHomeUIState
    object Loading : KidHomeUIState
    object Idle : KidHomeUIState
}
