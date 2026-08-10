package com.example.kidapp.presentation.screens.kidactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kidapp.data.remote.dto.ChildProfileResponse
import com.example.kidapp.domain.model.SearchTexts
import com.example.kidapp.domain.model.Video
import com.example.kidapp.domain.model.use_case.ChildrenUseCase
import com.example.kidapp.domain.model.use_case.GetSearchTextsUseCase
import com.example.kidapp.domain.model.use_case.GetWatchedVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class KidActivityViewModel @Inject constructor(
      private val getChildrenUseCase: ChildrenUseCase,
      private val getWatchedVideosUseCase: GetWatchedVideosUseCase,
      private val getSearchedTextsUseCase: GetSearchTextsUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow<KidActivityUIState>(KidActivityUIState.Idle)
    val uiState = _uiState.asStateFlow()
    private val _child = MutableStateFlow<List<ChildProfileResponse>>(emptyList())
    val child = _child.asStateFlow()

    private val _video = MutableStateFlow<List<Video>>(emptyList())
    val video = _video.asStateFlow()

    private val _searchedTexts = MutableStateFlow<List<SearchTexts>>(emptyList())
    val searchedTexts = _searchedTexts.asStateFlow()


    private val _isExpanded = MutableStateFlow(false)
    val isExpanded = _isExpanded.asStateFlow()

    private val _selectedName = MutableStateFlow("")
    val selectedName = _selectedName.asStateFlow()

    private val _selectedChildId = MutableStateFlow<Int?>(null)
    val selectedChildId = _selectedChildId.asStateFlow()

    fun onExpandClick() {
        _isExpanded.value = !_isExpanded.value
    }

    fun onSelectedNameChange(name: String) {
        _selectedName.value = name
    }

    fun onSelectedChildIdChange(id: Int) {
        _selectedChildId.value = id
    }

    fun getChildren(id: Int?) {
        viewModelScope.launch {
            _uiState.value = KidActivityUIState.Loading
            val result = getChildrenUseCase(id)
            result.onSuccess {
                _child.value = it
            }
        }
    }
    fun getWatchedVideosAndSearchTexts(childId: Int, name: String) {
        viewModelScope.launch {
            _uiState.value = KidActivityUIState.Loading

            val videosDeferred = async { getWatchedVideosUseCase(childId, name) }
            val searchTextsDeferred = async { getSearchedTextsUseCase(childId, name) }

            val videosResult = videosDeferred.await()
            val searchTextsResult = searchTextsDeferred.await()

            if (videosResult.isSuccess && searchTextsResult.isSuccess) {
                val videosList = videosResult.getOrDefault(emptyList()).map {
                    Video(
                        video_id = it.video_id,
                        title = it.title,
                        description = it.description,
                        thumbnail_url = it.thumbnail_url
                    )
                }
                val searchList = searchTextsResult.getOrDefault(emptyList()).map {
                    SearchTexts(
                        id = it.id,
                        query = it.query,
                        searchedAt = it.searchedAt
                    )
                }
                
                _video.value = videosList
                _searchedTexts.value = searchList
                
                _uiState.value = KidActivityUIState.Success(
                    watchedVideos = videosResult.getOrDefault(emptyList()),
                    searchTexts = searchTextsResult.getOrDefault(emptyList())
                )
            } else {
                _uiState.value = KidActivityUIState.Error("Failed to fetch page data")
            }
        }
    }


}
