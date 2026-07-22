package com.example.kidapp.presentation.screens.addprofile

import com.example.kidapp.domain.model.Child

sealed interface ChildProfileUIState{
    object Idle: ChildProfileUIState
    object Loading: ChildProfileUIState
    data class Success(val message: Child): ChildProfileUIState
    data class Error(val message: String): ChildProfileUIState
}