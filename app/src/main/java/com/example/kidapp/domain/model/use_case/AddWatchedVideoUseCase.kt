package com.example.kidapp.domain.model.use_case

import com.example.kidapp.data.remote.dto.WatchedVideoRes
import com.example.kidapp.domain.model.repository.AuthRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AddWatchedVideoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        childId: Int,
        videoId: String,
        watchedAt: LocalDateTime
    ): Result<WatchedVideoRes> =
        authRepository.addWatchedVideos(
            childId = childId,
            videoId = videoId,
            watchedAt = watchedAt
    )
}