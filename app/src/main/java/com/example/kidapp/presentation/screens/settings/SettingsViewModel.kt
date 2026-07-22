package com.example.kidapp.presentation.screens.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.R
import com.example.kidapp.data.remote.dto.ChildProfileResponse
import com.example.kidapp.domain.model.use_case.ChildrenUseCase
import com.example.kidapp.domain.model.use_case.DeleteChildUseCase
import com.example.kidapp.domain.model.use_case.UpdateChildUseCase
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
    private val getChildrenUseCase: ChildrenUseCase
): ViewModel() {

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

    private val _childSettingsUIState = MutableStateFlow<SettingsUIState>(SettingsUIState.Idle)
    val childSettingsUIState: StateFlow<SettingsUIState> = _childSettingsUIState

    private val _childName = mutableStateOf("")
    val childName: State<String> = _childName

    private val _childAge = mutableStateOf("")
    val childAge: State<String> = _childAge

    private val _childDeleteName = mutableStateOf("")
    val childDeleteName: State<String> = _childDeleteName

    private val isExpanded = mutableStateOf(false)
    val isExpandedState: State<Boolean> = isExpanded

    private val _selectedIcon = MutableStateFlow(0)
    val selectedIcon: StateFlow<Int> = _selectedIcon.asStateFlow()

    private val _isChoosingIcon = MutableStateFlow(false)
    val isChoosingIcon: StateFlow<Boolean> = _isChoosingIcon.asStateFlow()

    private val _child = MutableStateFlow<List<ChildProfileResponse>>(emptyList())
    val child: MutableStateFlow<List<ChildProfileResponse>> = _child

    private val _childIdUpdate = MutableStateFlow(0)
    val childIdUpdate: StateFlow<Int> = _childIdUpdate



    fun onChildIdChange(id: Int) {
        _childIdUpdate.value = id
    }
    private val _selectedName = MutableStateFlow("")
    var selectedName: StateFlow<String> = _selectedName

    fun onSelectedIconChange(selectedIcon: Int) {
        _selectedIcon.value = selectedIcon
    }
    fun updateIsChoosingIcon(isChoosingIcon: Boolean) {
        _isChoosingIcon.value = isChoosingIcon
    }

    fun onSelectedNameChange(newName: String) {
        _selectedName.value = newName
    }


    fun onExpandClick() {
        isExpanded.value = !isExpanded.value
    }

    fun onChildNameChange(newName: String) {
        _childName.value = newName
    }

    fun onChildAgeChange(newAge: String) {
        _childAge.value = newAge
    }


    fun onChildDeleteNameChange(newName: String) {
        _childDeleteName.value = newName
    }

    fun onUpdateClick() {
        viewModelScope.launch {
            _childSettingsUIState.value = SettingsUIState.Loading
            val iconKey = drawableToKeyMap[_selectedIcon.value] ?: "panda"
            val result = updateChildUseCase(
                name = _childName.value,
                birthDate = _childAge.value,
                iconProfile = iconKey,
                id = _childIdUpdate.value
            )
            result.onSuccess{
                _childSettingsUIState.value = SettingsUIState.Success(it)
                clearChildUpdate()
            }
            result.onFailure {
                _childSettingsUIState.value = SettingsUIState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun onDeleteClick(id: Int) {
        viewModelScope.launch {
            _childSettingsUIState.value = SettingsUIState.Loading
            val result = deleteChildUseCase(
                name = _childDeleteName.value,
                id = id
            )
            result.onSuccess{
                _childSettingsUIState.value = SettingsUIState.Success(child = null)
                clearChildDelete()
            }
            result.onFailure {
                _childSettingsUIState.value = SettingsUIState.Error(it.message ?: "Unknown error")
            }
        }
    }

    fun getChildren(id: Int?) {
        viewModelScope.launch {
            _childSettingsUIState.value = SettingsUIState.Loading
            val result = getChildrenUseCase(id)
            result.onSuccess{
                _child.value = it
            }
        }
    }

    fun clearChildUpdate() {
        _childName.value = ""
        _childAge.value = ""
        _childDeleteName.value = ""
    }
    fun clearChildDelete() {
        _childDeleteName.value = ""

    }

}