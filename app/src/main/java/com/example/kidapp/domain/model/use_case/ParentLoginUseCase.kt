package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class ParentLoginUseCase @Inject constructor(
    private val repository: AuthRepository
)
{
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repository.parentLogin(email,password)
}