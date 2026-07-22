package com.example.kidapp.presentation.screens.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SettingsUiState(
    val isDarkTheme: Boolean = false,
    val name : String = "",
    val email : String = "",
    val password : String = "",
    val confirmPassword : String = "",
    val showPassword : Boolean = false,
    val showConfirmPassword : Boolean = false,
    val showError : Boolean = false,
    val showSuccess : Boolean = false,
    val isChoosingIcon : Boolean = false,
    val isVerifying : Boolean = false,
    val verifyPassword : String = "",
    val selectedIcon : Int = 0,
    val kidName : String = "",
    val kidAge : String = "",
    val isParentUpdating : Boolean = false,
    val isParentDeleting : Boolean = false,
    val isKidDeleting : Boolean = false,
    val isKidUpdating : Boolean = false,
    val isKidAdding : Boolean = false,
    val deleteEmail: String = "",
    val updateKidName: String = "",
    val updateKidAge: String = "",
    val kidDeleteName: String = "",
)
class SettingsViewModel2 : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    fun updateName(name : String) {
        _uiState.value = _uiState.value.copy(name = name)

    }
    fun updateEmail(email : String) {
        _uiState.value = _uiState.value.copy(email = email)
    }
    fun updatePassword(password : String) {
        _uiState.value = _uiState.value.copy(password = password)
    }
    fun updateConfirmPassword(confirmPassword : String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }
    fun updateShowPassword(showPassword : Boolean) {
        _uiState.value = _uiState.value.copy(showPassword = showPassword)
    }
    fun updateShowConfirmPassword(showConfirmPassword : Boolean) {
        _uiState.value = _uiState.value.copy(showConfirmPassword = showConfirmPassword)
    }
    fun updateChoosingIcon(isChoosingIcon : Boolean) {
        _uiState.value = _uiState.value.copy(isChoosingIcon = isChoosingIcon)

    }
    fun updateVerifying(isVerifying : Boolean) {
        _uiState.value = _uiState.value.copy(isVerifying = isVerifying)
    }
    fun updateVerifyPassword(verifyPassword : String) {
        _uiState.value = _uiState.value.copy(verifyPassword = verifyPassword)
    }
    fun updateSelectedIcon(selectedIcon : Int) {
        _uiState.value = _uiState.value.copy(selectedIcon = selectedIcon)
    }
    fun updateKidName(kidName : String) {
        _uiState.value = _uiState.value.copy(kidName = kidName)

    }
    fun updateKidAge(kidAge : String) {
        _uiState.value = _uiState.value.copy(kidAge = kidAge)
    }
    fun updateParentUpdating(isUpdating : Boolean) {
        _uiState.value = _uiState.value.copy(isParentUpdating = isUpdating)
    }
    fun updateParentDeleting(isDeleting : Boolean) {
        _uiState.value = _uiState.value.copy(isParentDeleting = isDeleting)
    }
    fun updateKidUpdating(isUpdating : Boolean) {
        _uiState.value = _uiState.value.copy(isKidUpdating = isUpdating)
    }
    fun updateKidDeleting(isDeleting : Boolean) {
        _uiState.value = _uiState.value.copy(isKidDeleting = isDeleting)
    }
    fun updateEmailName(deleteEmail : String) {
        _uiState.value = _uiState.value.copy(deleteEmail = deleteEmail)
    }

    fun updateUpdatingKidName(updateKidName : String) {
        _uiState.value = _uiState.value.copy(updateKidName = updateKidName)
    }
    fun updateUpdatingKidAge(updateKidAge : String) {
        _uiState.value = _uiState.value.copy(updateKidAge = updateKidAge)
    }
    fun updateDeletingKidName(kidDeleteName : String) {
        _uiState.value = _uiState.value.copy(kidDeleteName = kidDeleteName)
    }
}

