package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class GetParentUseCase @Inject constructor(
    private val repository: AuthRepository
)
{
    suspend operator fun invoke(id: Int?) = repository.getParent(id)
}