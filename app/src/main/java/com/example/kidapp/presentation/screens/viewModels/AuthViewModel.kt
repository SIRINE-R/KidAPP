package com.example.myapplication.kid_activity.presentation.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LoginUiState(
    val loginEmail: String = "",
    val loginPassword: String = "",
)
data class RegisterUiState(
    val registerEmail: String = "",
    val registerPassword: String = "",
    val registerName: String = "",
)
class AuthViewModel : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()
    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState = _registerUiState.asStateFlow()

    fun updateLoginEmail(email: String) {
        _loginUiState.value = _loginUiState.value.copy(loginEmail = email)
    }
    fun updateLoginPassword(password: String) {
        _loginUiState.value = _loginUiState.value.copy(loginPassword = password)

    }
    fun updateRegisterEmail(email : String) {
        _registerUiState.value = _registerUiState.value.copy(registerEmail = email)
    }
    fun updateRegisterPassword(password: String) {
        _registerUiState.value = _registerUiState.value.copy(registerPassword = password)
    }
    fun updateRegisterName(name: String) {
        _registerUiState.value = _registerUiState.value.copy(registerName = name)
    }




}