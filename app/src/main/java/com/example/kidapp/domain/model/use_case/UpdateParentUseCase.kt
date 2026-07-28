package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class UpdateParentUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(id: Int?, email: String, password: String) =
        authRepository.updateParent(id,email, password)
}
