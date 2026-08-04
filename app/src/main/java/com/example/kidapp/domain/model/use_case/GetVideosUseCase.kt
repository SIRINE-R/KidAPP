package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject


class GetVideosUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(query: String) = authRepository.getVideos(query = query)
}