package com.example.kidapp.presentation.screens.verification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.kidapp.domain.model.use_case.VerifyParentUseCase
import kotlinx.coroutines.launch

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val verifyParentUseCase: VerifyParentUseCase
): ViewModel() {

    private val _verifyUiState = MutableStateFlow<VerifyUIState>(VerifyUIState.Idle)
    val verifyUiState: StateFlow<VerifyUIState> = _verifyUiState

    private val _verifyPassword = MutableStateFlow("")
    val verifyPassword: StateFlow<String> = _verifyPassword



    fun onVerifyPasswordChange(newPassword: String) {
        _verifyPassword.value = newPassword
    }



    fun onVerifyClick(email: String?) {
        viewModelScope.launch {
            _verifyUiState.value = VerifyUIState.Loading
            val result = verifyParentUseCase(
                password = _verifyPassword.value,
                email = email
            )
            result.onSuccess {
                _verifyUiState.value = VerifyUIState.Success(it)
            }
            result.onFailure {
                _verifyUiState.value = VerifyUIState.Error(it.message ?: "Unknown error")
            }
        }

        }
    }
