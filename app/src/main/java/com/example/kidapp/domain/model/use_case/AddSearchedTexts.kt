package com.example.kidapp.domain.model.use_case

import com.example.kidapp.domain.model.repository.AuthRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AddSearchedTexts @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        id: Int,
        query: String,
        searchedAt: LocalDateTime
    ) = repository.addSearchText(id, query, searchedAt)
}
