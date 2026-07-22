package com.example.kidapp.presentation.screens.kidscreen

sealed interface KidHomeUIState{
    data class Success(val children: List<String>) : KidHomeUIState
    data class Error(val message: String) : KidHomeUIState
    object Loading : KidHomeUIState
    object Idle : KidHomeUIState
}
