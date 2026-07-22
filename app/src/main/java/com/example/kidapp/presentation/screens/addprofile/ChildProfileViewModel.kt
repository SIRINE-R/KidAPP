package com.example.kidapp.presentation.screens.addprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.R
import com.example.kidapp.domain.model.use_case.ChildProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildProfileViewModel @Inject constructor(
    private val childProfileUseCase: ChildProfileUseCase
): ViewModel(){

    private val drawableToKeyMap = mapOf(
        R.drawable.rabbit to "rabbit",
        R.drawable.bob to "bob",
        R.drawable.bird to "bird",
        R.drawable.dorimen to "dorimen",
        R.drawable.jerry to "jerry",
        R.drawable.mikymouse to "mikymouse",
        R.drawable.panda to "panda"
    )
    private val _uiState = MutableStateFlow<ChildProfileUIState>(ChildProfileUIState.Idle)
    val uiState: StateFlow<ChildProfileUIState> = _uiState.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    private val _selectedIcon = MutableStateFlow(R.drawable.panda)
    val selectedIcon: StateFlow<Int> = _selectedIcon.asStateFlow()

    private val _isChoosingIcon = MutableStateFlow(false)
    val isChoosingIcon: StateFlow<Boolean> = _isChoosingIcon.asStateFlow()

    fun updateIsChoosingIcon(isChoosingIcon: Boolean) {
        _isChoosingIcon.value = isChoosingIcon
    }

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onBirthDateChange(birthDate: String) {
        _birthDate.value = birthDate
    }

    fun onSelectedIconChange(selectedIcon: Int) {
        _selectedIcon.value = selectedIcon
    }

    fun onCreateProfileClick(id: Int?) {
        viewModelScope.launch {
            _uiState.value = ChildProfileUIState.Loading
            val iconKey = drawableToKeyMap[_selectedIcon.value] ?: "panda"
            val result = childProfileUseCase(
                name = _name.value,
                birthDate = _birthDate.value,
                iconProfile = iconKey,
                parentId = id
            )
            result.onSuccess {
                _uiState.value = ChildProfileUIState.Success(it)
            }
            result.onFailure {
                _uiState.value = ChildProfileUIState.Error(it.message ?: "Unknown error")
            }
        }
    }
}
