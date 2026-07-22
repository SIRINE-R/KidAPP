package com.example.kidapp.presentation.screens.kidscreen

import androidx.lifecycle.ViewModel
import com.example.kidapp.R
import com.example.kidapp.domain.model.use_case.ChildrenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class KidHomeScreenViewModel @Inject constructor(
    private val childrenUseCase: ChildrenUseCase): ViewModel()
{
    private val drawableToKeyMap = mapOf(
        R.drawable.rabbit to "rabbit",
        R.drawable.bob to "bob",
        R.drawable.bird to "bird",
        R.drawable.dorimen to "dorimen",
        R.drawable.jerry to "jerry",
        R.drawable.mikymouse to "mikymouse",
        R.drawable.panda to "panda"
    )
    private val _uiState = MutableStateFlow<KidHomeUIState>(KidHomeUIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val keyToDrawableMap = drawableToKeyMap.entries.associate { it.value to it.key }


    fun getDrawableRes(iconKey: String?): Int {
        return keyToDrawableMap[iconKey] ?: R.drawable.panda
    }

    }

