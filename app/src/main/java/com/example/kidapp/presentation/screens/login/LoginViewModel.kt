package com.example.kidapp.presentation.screens.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.domain.model.use_case.ParentLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val parentLoginUseCase: ParentLoginUseCase
): ViewModel() {

    private val _loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val loginUIState: StateFlow<LoginUIState> = _loginUIState

    private val _email = mutableStateOf("")
    val email: State<String> = _email
    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _id = mutableStateOf(0)
    val id: State<Int> = _id
    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _loginUIState.value = LoginUIState.Loading
            val result = parentLoginUseCase(
                email = _email.value,
                password = _password.value,
            )
            result.onSuccess { parent ->
                _id.value = parent.id
                _loginUIState.value = LoginUIState.Success(parent)
            }
            result.onFailure {
                _loginUIState.value = LoginUIState.Error(it.message ?: "Unknown error")
            }
        }
    }
}