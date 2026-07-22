package com.example.kidapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ParentResponseDto(
    val id: Int,
    val name: String,
    val email: String
)
@Serializable
data class ParentRequestDto(
    val name: String,
    val email: String,
    val password: String)
