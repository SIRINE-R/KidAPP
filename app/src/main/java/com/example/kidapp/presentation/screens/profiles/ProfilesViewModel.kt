package com.example.kidapp.presentation.screens.profiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.R
import com.example.kidapp.domain.model.ChildProfile
import com.example.kidapp.domain.model.Parent
import com.example.kidapp.domain.model.use_case.ChildrenUseCase
import com.example.kidapp.domain.model.use_case.GetParentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilesViewModel @Inject constructor(
    private val childrenUseCase: ChildrenUseCase,
    private val getParentUseCase: GetParentUseCase
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
    private val _childrenUIState = MutableStateFlow<ProfilesUIState>(ProfilesUIState.Idle)
    val childrenUIState: StateFlow<ProfilesUIState> = _childrenUIState.asStateFlow()

    private val _children = MutableStateFlow<List<ChildProfile>>(emptyList())
    val children: StateFlow<List<ChildProfile>> = _children.asStateFlow()
    
    private val _parent = MutableStateFlow<Parent?>(null)
    val parent: StateFlow<Parent?> = _parent.asStateFlow()

    private val _childId = MutableStateFlow(0)
    val childId: StateFlow<Int> = _childId.asStateFlow()

    fun onChildIdChange(id: Int) {
        _childId.value = id
    }

    private val keyToDrawableMap = drawableToKeyMap.entries.associate { it.value to it.key }

    fun getChildren(parentId: Int?) {
        if (parentId == null) {
            _childrenUIState.value = ProfilesUIState.Error("Parent ID is required")
            return
        }

        viewModelScope.launch {
            _childrenUIState.value = ProfilesUIState.Loading
            childrenUseCase(parentId)
                .onSuccess { childResponses ->
                    _children.value = childResponses.map { response ->
                        ChildProfile(
                            name = response.name,
                            iconProfile = response.iconProfile,
                            id = response.id
                        )
                    }
                    _childrenUIState.value = ProfilesUIState.Success(childResponses)
                    onChildIdChange(childResponses[0].id)
                }
                .onFailure { exception ->
                    _childrenUIState.value = ProfilesUIState.Error(exception.message ?: "Unknown error")
                }
        }
    }

    fun getParent(parentId: Int?) {
        if (parentId == null) return

        viewModelScope.launch { 
            getParentUseCase(parentId)
                .onSuccess { parentData ->
                    _parent.value = parentData
                }
        }
    }

    fun getProfileData(parentId: Int?) {
        if (parentId == null) return
        getChildren(parentId)
        getParent(parentId)
    }

    fun getDrawableRes(iconKey: String?): Int {
        return keyToDrawableMap[iconKey] ?: R.drawable.panda
    }
}