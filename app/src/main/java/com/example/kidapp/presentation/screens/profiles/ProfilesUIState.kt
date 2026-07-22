package com.example.kidapp.presentation.screens.profiles

import com.example.kidapp.data.remote.dto.ChildProfileResponse
import com.example.kidapp.domain.model.Parent

sealed interface ProfilesUIState {
    object Idle : ProfilesUIState
    object Loading : ProfilesUIState
    data class Success(val message: List<ChildProfileResponse>) : ProfilesUIState
    data class Error(val message: String) : ProfilesUIState
}
