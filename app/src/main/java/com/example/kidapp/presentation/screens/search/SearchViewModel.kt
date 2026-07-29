package com.example.kidapp.presentation.screens.search

import androidx.lifecycle.ViewModel
import com.example.kidapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<SerachScreenUIState>(SerachScreenUIState.Idle)
    val uiState = _uiState.asStateFlow()

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

}