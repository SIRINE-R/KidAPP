package com.example.kidapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateParentReq(
    val id: Int?,
    val email: String,
    val password: String
)

@Serializable
data class UpdateParentRes(
    val email: String,
    val password: String
)