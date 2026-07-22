package com.example.kidapp.presentation.screens.settings

import com.example.kidapp.domain.model.Child

sealed interface SettingsUIState{
    object Idle: SettingsUIState
    object Loading: SettingsUIState
    data class Success(val child: Child?): SettingsUIState
    data class Error(val message: String): SettingsUIState
}
