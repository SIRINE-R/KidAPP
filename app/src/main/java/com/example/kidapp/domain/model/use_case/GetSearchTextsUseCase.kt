package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class GetSearchTextsUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(childId: Int, name: String) = authRepository.getSearchTexts(childId, name)
}
