package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class ChildrenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        parentId: Int?
    ) = repository.retrieveChildProfiles(parentId)
}