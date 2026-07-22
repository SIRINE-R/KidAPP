package com.example.kidapp.domain.model.use_case
import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class SignUpParentUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ) = repository.parentSignUp(name, email, password)
}
