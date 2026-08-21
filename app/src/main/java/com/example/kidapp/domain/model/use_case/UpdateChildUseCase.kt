package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.Child
import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class UpdateChildUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        birthDate: String,
        iconProfile: String,
        id: Int?
    ) = authRepository.updateChild(name, birthDate, iconProfile, id)
}