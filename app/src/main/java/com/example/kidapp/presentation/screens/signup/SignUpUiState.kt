package com.example.kidapp.presentation.screens.signup

import com.example.kidapp.domain.model.Parent

sealed interface SignUpUiState{
    object Idle: SignUpUiState
    object Loading: SignUpUiState
    data class Success(val parent: Parent): SignUpUiState
    data class Error(val message: String): SignUpUiState
}