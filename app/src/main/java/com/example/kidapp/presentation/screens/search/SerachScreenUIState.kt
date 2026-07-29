package com.example.kidapp.presentation.screens.search

sealed interface SerachScreenUIState {
    object Idle : SerachScreenUIState
    object Loading : SerachScreenUIState
    data class Success(val videos: List<String>) : SerachScreenUIState
    data class Error(val message: String) : SerachScreenUIState
}