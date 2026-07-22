package com.example.kidapp.presentation.screens.signup


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.domain.model.use_case.SignUpParentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Patterns.EMAIL_ADDRESS
import io.ktor.serialization.JsonConvertException
import java.net.ConnectException

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpParentUseCase: SignUpParentUseCase
): ViewModel() {

    private val _registerUiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val registerUiState: StateFlow<SignUpUiState> = _registerUiState

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _nameError = mutableStateOf<String?>(null)
    val nameError: State<String?> = _nameError

    private val _emailError = mutableStateOf<String?>(null)
    val emailError: State<String?> = _emailError

    private val _passwordError = mutableStateOf<String?>(null)
    val passwordError: State<String?> = _passwordError
    fun isInputValid(values: List<String>): Boolean {
        _nameError.value = null
        _emailError.value = null
        _passwordError.value = null

        if (_name.value.isBlank()) {
            _nameError.value = values[0]
        }
        if (_email.value.isBlank()) {
            _emailError.value = values[1]
        } else if (!EMAIL_ADDRESS.matcher(_email.value).matches()) {
            _emailError.value = values[2]
        }
        if (_password.value.isBlank()) {
            _passwordError.value = values[3]
        } else if (_password.value.length < 7) {
            _passwordError.value = values[4]
        }

        return _nameError.value == null &&
                _emailError.value == null &&
                _passwordError.value == null
        }

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onSignUpClick(values: List<String>) {
        if(isInputValid(values)) {
            return
        }
        viewModelScope.launch {
            _registerUiState.value = SignUpUiState.Loading
            val result = signUpParentUseCase(
                name = _name.value,
                email = _email.value,
                password = _password.value
            )
            result.onSuccess {
                _registerUiState.value = SignUpUiState.Success(it)
            }
            result.onFailure { exception ->
                val friendlyMessage = when (exception) {
                    is JsonConvertException -> values[5]
                    is ConnectException -> values[6]
                    else -> exception.message ?: values[7]
                }
                _registerUiState.value = SignUpUiState.Error(friendlyMessage)
            }
        }
    }


}