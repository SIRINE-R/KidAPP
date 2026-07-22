package com.example.kidapp.presentation.screens.login

import com.example.kidapp.domain.model.Parent

sealed interface LoginUIState{
    object Idle: LoginUIState
    object Loading: LoginUIState
    data class Success(val parent: Parent) : LoginUIState
    data class Error(val message: String) : LoginUIState
}