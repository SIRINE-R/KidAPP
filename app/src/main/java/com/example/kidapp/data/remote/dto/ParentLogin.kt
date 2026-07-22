package com.example.kidapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParentLogInRequest(
    val email: String,
    val password: String
)
@Serializable
data class ParentLoginResponseDto(
    val id: Int,
    val name: String,
    val email: String
)