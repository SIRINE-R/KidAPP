package com.example.kidapp.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.R
import com.example.kidapp.domain.model.Child
import com.example.kidapp.domain.model.use_case.ChildrenUseCase
import com.example.kidapp.domain.model.use_case.DeleteChildUseCase
import com.example.kidapp.domain.model.use_case.DeleteParentUseCase
import com.example.kidapp.domain.model.use_case.UpdateChildUseCase
import com.example.kidapp.domain.model.use_case.UpdateParentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateChildUseCase: UpdateChildUseCase,
    private val deleteChildUseCase: DeleteChildUseCase,
    private val getChildrenUseCase: ChildrenUseCase,
    private val updateParentUseCase: UpdateParentUseCase,
    private val deleteParentUseCase: DeleteParentUseCase
): ViewModel() {

    data class SettingsFormState(
        val isParentUpdating: Boolean = false,
        val isParentDeleting: Boolean = false,
        val isKidUpdating: Boolean = false,
        val isKidDeleting: Boolean = false,
        val emailUpdate: String = "",
        val passwordUpdate: String = "",
        val deleteEmail: String? = "",
        val childName: String = "",
        val childAge: String = "",
        val childDeleteName: String = "",
        val selectedName: String = "",
        val selectedIcon: Int = 0,
        val isChoosingIcon: Boolean = false,
        val isExpanded: Boolean = false,
        val childIdUpdate: Int? = 0
    )

    private val drawableToKeyMap = mapOf(
        R.drawable.rabbit to "rabbit",
        R.drawable.bob to "bob",
        R.drawable.bird to "bird",
        R.drawable.dorimen to "dorimen",
        R.drawable.jerry to "jerry",
        R.drawable.mikymouse to "mikymouse",
        R.drawable.panda to "panda"
    )

    private val keyToDrawableMap = drawableToKeyMap.entries.associate { it.value to it.key }

    fun getDrawableRes(iconKey: String?): Int {
        return keyToDrawableMap[iconKey] ?: R.drawable.panda
    }

    private val _settingsUIState = MutableStateFlow<SettingsUIState>(SettingsUIState.Idle)
    val settingsUIState: StateFlow<SettingsUIState> = _settingsUIState

    private val _formState = MutableStateFlow(SettingsFormState())
    val formState: StateFlow<SettingsFormState> = _formState.asStateFlow()

    private val _child = MutableStateFlow<List<Child>>(emptyList())
    val child: MutableStateFlow<List<Child>> = _child

    fun onEmailUpdate(email: String?) {
        if (email != null) {
            _formState.value = _formState.value.copy(emailUpdate = email)
        }
    }

    fun onPasswordUpdate(password: String) {
        _formState.value = _formState.value.copy(passwordUpdate = password)
    }

    fun updateParentUpdating(isUpdating: Boolean) {
        _formState.value = _formState.value.copy(isParentUpdating = isUpdating)
    }

    fun updateParentDeleting(isDeleting: Boolean) {
        _formState.value = _formState.value.copy(isParentDeleting = isDeleting)
    }

    fun updateKidUpdating(isUpdating: Boolean) {
        _formState.value = _formState.value.copy(isKidUpdating = isUpdating)
    }

    fun updateKidDeleting(isDeleting: Boolean) {
        _formState.value = _formState.value.copy(isKidDeleting = isDeleting)
    }

    fun deleteParentEmail(deleteEmail: String?) {
        _formState.value = _formState.value.copy(deleteEmail = deleteEmail)
    }

    fun onChildIdChange(id: Int?) {
        _formState.value = _formState.value.copy(childIdUpdate = id)
    }

    fun onSelectedIconChange(selectedIcon: Int) {
        _formState.value = _formState.value.copy(selectedIcon = selectedIcon)
    }

    fun updateIsChoosingIcon(isChoosingIcon: Boolean) {
        _formState.value = _formState.value.copy(isChoosingIcon = isChoosingIcon)
    }

    fun onSelectedNameChange(newName: String) {
        _formState.value = _formState.value.copy(selectedName = newName)
    }

    fun onExpandClick() {
        _formState.value = _formState.value.copy(isExpanded = !_formState.value.isExpanded)
    }

    fun onChildNameChange(newName: String) {
        _formState.value = _formState.value.copy(childName = newName)
    }

    fun onChildAgeChange(newAge: String) {
        _formState.value = _formState.value.copy(childAge = newAge)
    }

    fun onChildDeleteNameChange(newName: String) {
        _formState.value = _formState.value.copy(childDeleteName = newName)
    }

    fun onUpdateChildClick() {
        viewModelScope.launch {
            _settingsUIState.value = SettingsUIState.Loading
            val iconKey = drawableToKeyMap[_formState.value.selectedIcon] ?: "panda"
            val result = updateChildUseCase(
                name = _formState.value.childName,
                birthDate = _formState.value.childAge,
                iconProfile = iconKey,
                id = _formState.value.childIdUpdate
            )
            result.onSuccess {
                _settingsUIState.value = SettingsUIState.Success(child = it, parent = null)
                clearChildUpdate()
            }
            result.onFailure {
                _settingsUIState.value = SettingsUIState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun onDeleteChildClick(id: Int?) {
        viewModelScope.launch {
            _settingsUIState.value = SettingsUIState.Loading
            val result = deleteChildUseCase(
                name = _formState.value.childDeleteName,
                id = id
            )
            result.onSuccess {
                _settingsUIState.value = SettingsUIState.Success(child = null, parent = null)
                clearChildDelete()
            }
            result.onFailure {
                _settingsUIState.value = SettingsUIState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun onUpdateParentClick(id: Int?) {
        viewModelScope.launch {
            _settingsUIState.value = SettingsUIState.Loading
            val result = updateParentUseCase(
                id = id,
                email = _formState.value.emailUpdate,
                password = _formState.value.passwordUpdate
            )
            result.onSuccess {
                _settingsUIState.value = SettingsUIState.Success(child = null, parent = it)
                clearParentUpdate()
            }
            result.onFailure {
                _settingsUIState.value = SettingsUIState.Error(it.message ?: "Unknown error")
            }
        }
    }
    fun onDeleteParentClick(email: String) {
        viewModelScope.launch {
            _settingsUIState.value = SettingsUIState.Loading
            val result = deleteParentUseCase(
               email = email
            )
            result.onSuccess {
                _settingsUIState.value = SettingsUIState.Success(child = null, parent = null)
                clearParentDelete()
            }
            result.onFailure {
                _settingsUIState.value = SettingsUIState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun getChildren(id: Int?) {
        viewModelScope.launch {
            _settingsUIState.value = SettingsUIState.Loading
            val result = getChildrenUseCase(id)
            result.onSuccess { result ->
                _child.value = result.map {
                    Child(
                        id = it.id,
                        name = it.name,
                        birthDate = it.birthDate,
                        iconProfile = it.iconProfile,
                        parentId = id
                    )
                }

            }
        }
    }

    fun clearChildUpdate() {
        _formState.value = _formState.value.copy(
            childName = "",
            childAge = "",
            selectedIcon = 0,
            selectedName = "",
            isKidUpdating = false
        )
    }

    fun clearChildDelete() {
        _formState.value = _formState.value.copy(
            childDeleteName = "",
            isKidDeleting = false
        )
    }

    fun clearParentUpdate() {
        _formState.value = _formState.value.copy(
            emailUpdate = "",
            passwordUpdate = "",
            isParentUpdating = false
        )
    }
    fun clearParentDelete() {
        _formState.value = _formState.value.copy(
            deleteEmail = "",
            isParentDeleting = false
        )
    }
}