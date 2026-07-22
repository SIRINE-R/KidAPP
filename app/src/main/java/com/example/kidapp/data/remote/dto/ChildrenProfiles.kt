package com.example.kidapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChildProfileResponse(
    val id: Int,
    val name: String,
    @SerialName("icon_profile")
    val iconProfile: String,
    @SerialName("birth_date")
    val birthDate: String
)

