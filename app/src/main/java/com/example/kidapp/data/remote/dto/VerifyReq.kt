package com.example.kidapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class VerifyReq(
    val password: String,
    val email: String?
)
@Serializable
data class VerifyRes(
    val id: Int,
    val email: String
)