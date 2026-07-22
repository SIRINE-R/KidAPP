package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class DeleteChildUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        id: Int,
        name: String
    ) = authRepository.deleteChild(name = name,id = id)
}