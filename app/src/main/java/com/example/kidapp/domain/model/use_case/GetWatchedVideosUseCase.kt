package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class GetWatchedVideosUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(childId: Int, name: String) = repository.getWatchedVideos(childId, name)
}
