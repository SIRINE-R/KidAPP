package com.example.kidapp.presentation.screens.verification

import com.example.kidapp.domain.model.ParentVerify

sealed interface VerifyUIState {
    object Idle : VerifyUIState
    object Loading : VerifyUIState
    data class Success(val parent: ParentVerify) : VerifyUIState
    data class Error(val message: String) : VerifyUIState
}
