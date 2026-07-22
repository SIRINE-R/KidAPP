package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import javax.inject.Inject

class ChildProfileUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke(
        name: String,
        birthDate: String,
        iconProfile: String,
        parentId: Int?
    ) = repository.addChildProfile(name, birthDate, iconProfile, parentId)

}